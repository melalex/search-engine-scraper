package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.loader.internal.RequestContext;
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
public class DirectRequestStrategy extends AbstractRequestStrategy {
    private final Map<SearchEngine, LocalDateTime> direct = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    protected Mono<RequestContext> handle(RequestContext context) {
        int page = context.getPage().getNumber();
        String task = context.getTask().getId();
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getDelay());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime schedule = direct.compute(engine, (k, v) -> reserve(now, v, timeout));
        Duration duration = Duration.between(now, schedule);

        log.info("Schedule direct request on {} for TaskDto {}, page {} and Engine {}", schedule, task, page, engine);

        return Mono.delay(duration)
                .then(Mono.just(context));
    }

    @Override
    public void report(RequestContext context) {
        int page = context.getPage().getNumber();
        String task = context.getTask().getId();
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getErrorDelay());

        direct.compute(engine, (k, v) -> relax(v, timeout));

        log.info("Direct request error handled for TaskDto {} and Engine {} on {} page", task, engine, page);
    }

    private LocalDateTime reserve(LocalDateTime now, LocalDateTime previous, Duration duration) {
        if (previous == null || previous.isBefore(now)) {
            return now;
        }

        return previous.plus(duration);
    }

    private LocalDateTime relax(LocalDateTime previous, Duration duration) {
        return Optional.ofNullable(previous)
                .orElseThrow(() -> new IllegalStateException("report method should be called after await"))
                .plus(duration);
    }
}