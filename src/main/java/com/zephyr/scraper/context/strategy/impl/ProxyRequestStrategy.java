package com.zephyr.scraper.context.strategy.impl;

import com.zephyr.scraper.context.strategy.RequestStrategy;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.source.ProxySource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDateTime.now;

@Slf4j
@Component
public class ProxyRequestStrategy implements RequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ProxySource proxySource;

    @Override
    public Mono<RequestContext> configure(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        return proxySource.reserve(engine)
            .map(p -> builder.proxy(p).duration(Duration.between(now(), p.getSchedule())).build());
    }

    @Override
    public void report(RequestContext context) {
        proxySource.report(context.getProxy().getId(), context.getProvider())
                .subscribe();
    }
}