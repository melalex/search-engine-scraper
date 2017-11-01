package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.*;
import com.zephyr.scraper.loader.agent.AgentFactory;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.content.ResponseExtractor;
import com.zephyr.scraper.loader.internal.RequestContext;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.exceptions.AgentException;
import com.zephyr.scraper.loader.exceptions.RequestException;
import com.zephyr.scraper.loader.fraud.FraudAnalyzer;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class BrowserImpl implements Browser {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private AgentFactory agentFactory;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Setter(onMethod = @__(@Autowired))
    private ResponseExtractor responseExtractor;

    @Setter(onMethod = @__(@Autowired))
    private ContextManager contextManager;

    @Override
    public Mono<PageResponse> browse(Request request, PageRequest page) {
        return Mono.defer(() -> contextManager.await(request, page))
                .flatMap(this::makeRequest)
                .retryWhen(requestException());
    }

    private Mono<PageResponse> makeRequest(RequestContext context) {
        int page = context.getPage().getNumber();
        String task = context.getTask().getId();
        SearchEngine engine = context.getProvider();
        Map<String, ?> params = context.getPage().getParams();

        return agentFactory.create(context)
                .get(context.getFullUrl(), params)
                .map(r -> responseExtractor.extract(r, engine, page))
                .doOnNext(r -> log.info("Response extracted for TaskDto {} and Engine {} on {} page", task, engine, page))
                .doOnNext(r -> fraudAnalyzer.analyze(context, r))
                .retryWhen(webClientException())
                .onErrorMap(t -> new RequestException(t, context));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> webClientException() {
        return Retry.anyOf(AgentException.class)
                .retryMax(scraperProperties.getBrowser().getRetryCount())
                .doOnRetry(c -> log.error("WebClient throw exception {} on {} try", c.exception(), c.iteration()))
                .exponentialBackoff(
                        Duration.ofMillis(scraperProperties.getBrowser().getFirstBackoff()),
                        Duration.ofMillis(scraperProperties.getBrowser().getMaxBackoff())
                );
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.anyOf(RequestException.class)
                .doOnRetry(c -> report(c.exception()))
                .doOnRetry(c -> log.error("Request failed with exception {} on {} try", c.exception(), c.iteration()));
    }

    private void report(Throwable throwable) {
        if (throwable instanceof RequestException) {
            contextManager.report(((RequestException) throwable).getFailedRequest());
        }
    }
}