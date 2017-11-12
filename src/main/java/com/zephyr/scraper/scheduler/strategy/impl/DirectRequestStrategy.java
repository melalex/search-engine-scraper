package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.scheduler.strategy.RequestStrategy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DirectRequestStrategy implements RequestStrategy {
    private final Map<SearchEngine, LocalDateTime> state = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Mono<RequestContext> configureAndBuild(SearchEngine engine, RequestContext.RequestContextBuilder builder) {
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getDelay());
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime schedule = state.compute(engine, (k, v) -> reserve(now, v, timeout));

        return Mono.just(builder.duration(Duration.between(now, schedule)).build());
    }

    @Override
    public void report(RequestContext context) {
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getErrorDelay());

        state.compute(engine, (k, v) -> relax(v, timeout));
    }

    private LocalDateTime reserve(LocalDateTime now, LocalDateTime previous, Duration duration) {
        return previous == null || previous.isBefore(now) ? now : previous.plus(duration);
    }

    private LocalDateTime relax(LocalDateTime previous, Duration duration) {
        return Optional.ofNullable(previous)
                .orElseThrow(() -> new IllegalStateException("report method should be called after configureAndBuild"))
                .plus(duration);
    }
}