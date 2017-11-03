package com.zephyr.scraper.context;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Request;
import reactor.core.publisher.Mono;

public interface ContextManager {

    Mono<RequestContext> toContext(Request request);

    void report(RequestContext context);
}