package com.zephyr.scraper.loader.context.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@Component
public class ContextManagerImpl implements ContextManager {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy directRequestStrategy;

    @Setter(onMethod = @__(@Autowired(required = false)))
    private RequestStrategy proxyRequestStrategy;

    @PostConstruct
    public void init() {
        if (Objects.isNull(proxyRequestStrategy)) {
            log.warn("Proxy source bean is missing. Using directRequestStrategy.");
            proxyRequestStrategy = directRequestStrategy;
        }
    }

    @Override
    public Flux<RequestContext> toContext(Request request) {
        return Flux.fromIterable(request.getPages())
                .flatMap(p -> toContext(request.getProvider(), contextBuilder(request, p)));
    }

    @Override
    public Mono<RequestContext> retry(RequestContext context) {
        return getRequestStrategy(context.getProvider()).report(context)
                .doOnNext(v -> log.info("Error handled for RequestContext: {}", context))
                .then(toContext(context.getProvider(), contextBuilder(context)));
    }

    private RequestContext.RequestContextBuilder contextBuilder(Request request, PageRequest page) {
        return RequestContext.builder()
                .iteration(0)
                .taskId(request.getTask().getId())
                .languageIso(request.getTask().getLanguageIso())
                .userAgent(request.getTask().getUserAgent())
                .provider(request.getProvider())
                .baseUrl(request.getBaseUrl())
                .uri(request.getUri())
                .params(page.getParams())
                .number(page.getNumber());
    }

    private RequestContext.RequestContextBuilder contextBuilder(RequestContext context) {
        return RequestContext.builder()
                .iteration(context.getIteration() + 1)
                .taskId(context.getTaskId())
                .languageIso(context.getLanguageIso())
                .userAgent(context.getUserAgent())
                .provider(context.getProvider())
                .baseUrl(context.getBaseUrl())
                .uri(context.getUri())
                .params(context.getParams())
                .number(context.getNumber());
    }

    private Mono<RequestContext> toContext(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        return getRequestStrategy(engine).configure(engine, builder)
                .doOnNext(c -> log.info("New RequestContext: {}", c));
    }

    private RequestStrategy getRequestStrategy(SearchEngine engine) {
        return scraperProperties.getScraper(engine).isUseProxy() ? proxyRequestStrategy : directRequestStrategy;
    }
}