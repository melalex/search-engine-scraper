package com.zephyr.scraper.loader.context;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.context.model.RequestContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContextManager {

    Flux<RequestContext> toContext(Request request);

    Mono<RequestContext> retry(RequestContext context);
}
