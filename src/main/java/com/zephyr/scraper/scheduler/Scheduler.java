package com.zephyr.scraper.scheduler;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.RequestContext;
import reactor.core.publisher.Mono;

public interface Scheduler {

    Mono<RequestContext> createContext(EngineRequest engineRequest);

    void report(RequestContext context);
}