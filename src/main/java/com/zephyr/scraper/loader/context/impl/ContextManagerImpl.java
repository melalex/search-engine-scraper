package com.zephyr.scraper.loader.context.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.internal.RequestContext;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
            log.warn("Proxy source bean is missing. Use Direct request strategy");
            proxyRequestStrategy = directRequestStrategy;
        }
    }

    @Override
    public Mono<RequestContext> await(Request request, PageRequest page) {
        if (isUseProxy(request.getProvider())) {
            return proxyRequestStrategy.toContext(request, page);
        }

        return directRequestStrategy.toContext(request, page);
    }

    @Override
    public void report(RequestContext context) {
        if (isUseProxy(context.getProvider())) {
            proxyRequestStrategy.report(context);
        }

        directRequestStrategy.report(context);
    }

    private boolean isUseProxy(SearchEngine engine) {
        return scraperProperties.getScraper(engine).isUseProxy();
    }
}
