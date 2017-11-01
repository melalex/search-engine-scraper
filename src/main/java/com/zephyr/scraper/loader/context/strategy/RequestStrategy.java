package com.zephyr.scraper.loader.context.strategy;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.internal.RequestContext;
import reactor.core.publisher.Mono;

public interface RequestStrategy {

    Mono<RequestContext> toContext(Request request, PageRequest page);

    void report(RequestContext context);
}
