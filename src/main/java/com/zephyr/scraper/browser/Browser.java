package com.zephyr.scraper.browser;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.EngineResponse;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Browser {

    Mono<EngineResponse> get(RequestContext context);
}