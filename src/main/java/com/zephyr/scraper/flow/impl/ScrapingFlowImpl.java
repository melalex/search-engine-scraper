package com.zephyr.scraper.flow.impl;

import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.context.ContextManager;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.QueryConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

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
    private ContextManager contextManager;

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor queryConstructor;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Setter(onMethod = @__(@Autowired))
    private Crawler crawler;

    @Override
    public Flux<SearchResult> handle(Flux<Keyword> input) {
        return input.flatMap(queryConstructor::construct)
                .parallel()
                .flatMap(this::browse)
                .sequential();
    }

    private Mono<SearchResult> browse(Request request) {
        return Mono.defer(() -> contextManager.toContext(request))
                .flatMap(this::makeRequest);
    }

    private Mono<SearchResult> makeRequest(RequestContext context) {
        return Mono.delay(context.getDuration())
                .then(browser.get(context).retryWhen(browserException(context)))
                .map(r -> crawler.crawl(context.getProvider(), r))
                .retryWhen(requestException(context))
                .map(l -> toSearchResult(context, l));
    }

    private SearchResult toSearchResult(RequestContext request, List<String> links) {
        return SearchResult.builder()
                .offset(request.getOffset())
                .keyword(request.getKeyword())
                .provider(request.getProvider())
                .timestamp(LocalDateTime.now())
                .links(links)
                .build();
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> browserException(RequestContext context) {
        return Retry.<RequestContext>anyOf(BrowserException.class)
                .withApplicationContext(context)
                .retryMax(scraperProperties.getBrowser().getRetryCount())
                .doOnRetry(c -> log.info("Browser throw exception {} on {} try", c.exception(), c.iteration()))
                .exponentialBackoff(
                        Duration.ofMillis(scraperProperties.getBrowser().getFirstBackoff()),
                        Duration.ofMillis(scraperProperties.getBrowser().getMaxBackoff())
                );
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException(RequestContext context) {
        return Retry.<RequestContext>any()
                .withApplicationContext(context)
                .doOnRetry(c -> contextManager.report(c.applicationContext()))
                .doOnRetry(c -> log.info("Request failed with exception {} on {} try", c.exception(), c.iteration()));
    }
}