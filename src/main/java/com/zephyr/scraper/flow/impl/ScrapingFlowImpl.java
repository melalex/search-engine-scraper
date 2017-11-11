package com.zephyr.scraper.flow.impl;

import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.exceptions.RequestException;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.scheduler.Scheduler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;
import reactor.retry.RetryContext;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class ScrapingFlowImpl implements ScrapingFlow {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private Scheduler scheduler;

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor queryConstructor;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Setter(onMethod = @__(@Autowired))
    private Crawler crawler;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Flux<SearchResult> handle(Flux<Keyword> input) {
        return input.flatMap(queryConstructor::construct)
                .parallel()
                .flatMap(this::browse)
                .sequential();
    }

    private Mono<SearchResult> browse(Request request) {
        return Mono.defer(() -> scheduler.createContext(request))
                .flatMap(this::makeRequest)
                .retryWhen(requestException());
    }

    private Mono<SearchResult> makeRequest(RequestContext context) {
        return Mono.delay(context.getDuration())
                .then(Mono.defer(() -> browser.get(context)).retryWhen(browserException(context)))
                .map(r -> crawler.crawl(context.getProvider(), r))
                .map(l -> toSearchResult(context, l))
                .onErrorMap(t -> new RequestException(t, context));
    }

    private SearchResult toSearchResult(RequestContext request, List<String> links) {
        return SearchResult.builder()
                .offset(request.getOffset())
                .keyword(request.getKeyword())
                .provider(request.getProvider())
                .timestamp(LocalDateTime.now(clock))
                .links(links)
                .build();
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> browserException(RequestContext context) {
        return Retry.<RequestContext>anyOf(BrowserException.class)
                .withApplicationContext(context)
                .retryMax(scraperProperties.getBrowser().getRetryCount())
                .doOnRetry(c -> log.info("Browser throw exception {} on {} try", c.exception(), c.iteration()))
                .fixedBackoff(Duration.ofMillis(scraperProperties.getBrowser().getBackoff()));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.any()
                .doOnRetry(this::onRequestException);
    }

    private void onRequestException(RetryContext<Object> context) {
        log.info("Request failed with exception {} on {} try", context.exception(), context.iteration());

        if (context.exception() instanceof RequestException) {
            scheduler.report(((RequestException) context.exception()).getFailedContext());
        }
    }
}