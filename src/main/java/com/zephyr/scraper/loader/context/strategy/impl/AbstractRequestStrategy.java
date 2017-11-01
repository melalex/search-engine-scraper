package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.internal.RequestContext;
import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import reactor.core.publisher.Mono;

public abstract class AbstractRequestStrategy implements RequestStrategy {

    @Override
    public Mono<RequestContext> toContext(Request request, PageRequest page) {
        RequestContext context = new RequestContext();

        context.setTask(request.getTask());
        context.setProvider(request.getProvider());
        context.setBaseUrl(request.getBaseUrl());
        context.setUri(request.getUri());
        context.setPage(page);

        return handle(context);
    }

    protected abstract Mono<RequestContext> handle(RequestContext context);
}