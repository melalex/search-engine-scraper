package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.agent.Agent;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.content.ResponseExtractor;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.loader.exceptions.AgentException;
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
import java.util.function.Function;

@Slf4j
@Component
public class BrowserImpl implements Browser {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private Agent agent;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Setter(onMethod = @__(@Autowired))
    private ResponseExtractor responseExtractor;

    @Setter(onMethod = @__(@Autowired))
    private ContextManager contextManager;

    @Override
    public Mono<PageResponse> browse(RequestContext context) {
        return agent.get(context)
                .retryWhen(agentException())
                .map(r -> responseExtractor.extract(r, context.getProvider(), context.getNumber()))
                .doOnNext(r -> fraudAnalyzer.analyze(context, r))
                .onErrorResume(t -> retry(t, context))
                .doOnNext(p -> log.info("Load page #{} for Task {} and Engine {}",
                        context.getNumber(), context.getTaskId(), context.getProvider()));
    }

    private Mono<PageResponse> retry(Throwable throwable, RequestContext context) {
        log.error("Error during page loading. Retrying...", throwable);

        return contextManager.retry(context)
                .flatMap(this::browse);
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> agentException() {
        return Retry.anyOf(AgentException.class)
                .retryMax(scraperProperties.getBrowser().getRetryCount())
                .doOnRetry(c -> log.error("WebClient throw exception {} on {} try", c.exception(), c.iteration()))
                .exponentialBackoff(
                        Duration.ofMillis(scraperProperties.getBrowser().getFirstBackoff()),
                        Duration.ofMillis(scraperProperties.getBrowser().getMaxBackoff())
                );
    }
}