package com.zephyr.scraper.query;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.ScraperTask;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface QueryConstructor {

    Flux<Request> construct(ScraperTask task);
}
