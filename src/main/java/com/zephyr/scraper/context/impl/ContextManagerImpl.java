package com.zephyr.scraper.context.impl;

import com.zephyr.scraper.context.ContextManager;
import com.zephyr.scraper.context.strategy.RequestStrategy;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ContextManagerImpl implements ContextManager {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy directRequestStrategy;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy proxyRequestStrategy;

    @Override
    public Mono<RequestContext> toContext(Request request) {
        return Flux.fromIterable(request.getPages())
                .flatMap(p -> toContext(request.getProvider(), contextBuilder(request, p)));
    }

    @Override
    public Mono<RequestContext> retry(RequestContext context) {
        return getRequestStrategy(context.getProvider()).report(context)
                .doOnNext(v -> log.info("Error handled for RequestContext: {}", context))
                .then(toContext(context.getProvider(), contextBuilder(context)));
    }

    private RequestContext.RequestContextBuilder contextBuilder(Request request, RequestContext page) {
        return RequestContext.builder()
                .iteration(0)
                .keyword(request.getKeyword())
                .provider(request.getProvider())
                .baseUrl(request.getBaseUrl())
                .uri(request.getUri())
                .params(page.getParams())
                .page(page.getPage());
    }

    private RequestContext.RequestContextBuilder contextBuilder(RequestContext context) {
        return RequestContext.builder()
                .iteration(context.getIteration() + 1)
                .keyword(context.getKeyword())
                .provider(context.getProvider())
                .baseUrl(context.getBaseUrl())
                .uri(context.getUri())
                .params(context.getParams())
                .page(context.getPage());
    }

    private Mono<RequestContext> toContext(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        return getRequestStrategy(engine).configure(engine, builder)
                .doOnNext(c -> log.info("New RequestContext: {}", c));
    }

    private RequestStrategy getRequestStrategy(SearchEngine engine) {
        return scraperProperties.getScraper(engine).isUseProxy() ? proxyRequestStrategy : directRequestStrategy;
    }
}