package com.zephyr.scraper.loader.agent;

import com.zephyr.scraper.loader.agent.model.AgentResponse;
import com.zephyr.scraper.loader.context.model.RequestContext;
import reactor.core.publisher.Mono;

public interface Agent {

    Mono<AgentResponse> get(RequestContext context);
}