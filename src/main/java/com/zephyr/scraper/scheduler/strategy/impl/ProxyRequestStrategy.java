package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.scheduler.strategy.RequestStrategy;
import com.zephyr.scraper.source.ProxySource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ProxyRequestStrategy implements RequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ProxySource proxySource;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Mono<RequestContext> configureAndBuild(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        return proxySource.reserve(engine)
            .map(p -> builder.proxy(p).duration(Duration.between(LocalDateTime.now(clock), p.getSchedule())).build());
    }

    @Override
    public void report(RequestContext context) {
        proxySource.report(context.getProxy().getId(), context.getProvider())
                .subscribe();
    }
}