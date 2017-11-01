package com.zephyr.scraper.loader.context;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.internal.RequestContext;
import reactor.core.publisher.Mono;

public interface ContextManager {

    Mono<RequestContext> await(Request request, PageRequest page);

    void report(RequestContext context);
}
