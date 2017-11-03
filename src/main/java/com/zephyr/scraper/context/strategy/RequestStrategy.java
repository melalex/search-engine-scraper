package com.zephyr.scraper.context.strategy;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import reactor.core.publisher.Mono;

public interface RequestStrategy {

    Mono<RequestContext> configure(SearchEngine engine, RequestContext.RequestContextBuilder builder);

    Mono<Void> report(RequestContext context);
}
