package com.zephyr.scraper.browser;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Response;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Browser {

    Mono<Response> get(RequestContext context);
}