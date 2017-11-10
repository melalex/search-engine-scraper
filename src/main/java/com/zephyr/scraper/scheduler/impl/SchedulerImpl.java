package com.zephyr.scraper.scheduler.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.scheduler.Scheduler;
import com.zephyr.scraper.scheduler.strategy.RequestStrategy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@Component
public class SchedulerImpl implements Scheduler {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy directRequestStrategy;

    @Setter(onMethod = @__(@Autowired(required = false)))
    private RequestStrategy proxyRequestStrategy;

    @PostConstruct
    public void init() {
        if (Objects.isNull(proxyRequestStrategy)) {
            log.warn("ProxySource bean is missing. Use direct strategy");
            proxyRequestStrategy = directRequestStrategy;
        }
    }

    @Override
    public Mono<RequestContext> createContext(Request request) {
        return getRequestStrategy(request.getProvider())
                .configureAndBuild(request.getProvider(), RequestContext.builder().request(request))
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