package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.context.ProxySource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDateTime.now;

@Slf4j
@Component
@ConditionalOnBean(ProxySource.class)
public class ProxyRequestStrategy implements RequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ProxySource proxySource;

    @Override
    public Mono<RequestContext> configure(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        return proxySource.reserve(engine)
            .map(p -> builder.proxy(p).duration(Duration.between(now(), p.getSchedule())).build());
    }

    @Override
    public Mono<Void> report(RequestContext context) {
        return proxySource.report(context.getProxy().getId(), context.getProvider())
                .then();
    }
}