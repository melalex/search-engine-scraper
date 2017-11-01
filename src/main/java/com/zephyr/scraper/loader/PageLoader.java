package com.zephyr.scraper.loader;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface PageLoader {

    Mono<Response> load(Request request);
}
