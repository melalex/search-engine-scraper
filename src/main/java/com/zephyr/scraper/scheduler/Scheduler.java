package com.zephyr.scraper.scheduler;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import reactor.core.publisher.Mono;

public interface Scheduler {

    Mono<RequestContext> createContext(Request request);

    void report(RequestContext context);
}