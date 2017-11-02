package com.zephyr.scraper.loader.context.strategy;

import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.context.model.RequestContext;
import reactor.core.publisher.Mono;

public interface RequestStrategy {

    Mono<RequestContext> configure(SearchEngine engine, RequestContext.RequestContextBuilder builder);

    Mono<Void> report(RequestContext context);
}
