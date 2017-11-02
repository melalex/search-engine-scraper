package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DirectRequestStrategy implements RequestStrategy {
    private final Map<SearchEngine, LocalDateTime> direct = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    public Mono<RequestContext> configure(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getDelay());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime schedule = direct.compute(engine, (k, v) -> reserve(now, v, timeout));

        return Mono.just(builder.duration(Duration.between(now, schedule)).build());
    }

    @Override
    public Mono<Void> report(RequestContext context) {
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getErrorDelay());

        direct.compute(engine, (k, v) -> relax(v, timeout));

        return Mono.empty();
    }

    private LocalDateTime reserve(LocalDateTime now, LocalDateTime previous, Duration duration) {
        return previous == null || previous.isBefore(now) ? now : previous.plus(duration);
    }

    private LocalDateTime relax(LocalDateTime previous, Duration duration) {
        return Optional.ofNullable(previous)
                .orElseThrow(() -> new IllegalStateException("report method should be called after configure"))
                .plus(duration);
    }
}