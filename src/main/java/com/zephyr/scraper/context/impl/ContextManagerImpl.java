package com.zephyr.scraper.context.impl;

import com.zephyr.scraper.context.ContextManager;
import com.zephyr.scraper.context.strategy.RequestStrategy;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        return getRequestStrategy(request.getProvider())
                .configure(request.getProvider(), RequestContext.builder().request(request))
                .doOnNext(c -> log.info("New RequestContext: {}", c));
    }

    @Override
    public void report(RequestContext context) {
        log.info("Reporting RequestContext: {}", context);
        getRequestStrategy(context.getProvider()).report(context);
    }

    private RequestStrategy getRequestStrategy(SearchEngine engine) {
        return scraperProperties.getScraper(engine).isUseProxy() ? proxyRequestStrategy : directRequestStrategy;
    }
}