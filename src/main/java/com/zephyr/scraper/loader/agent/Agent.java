package com.zephyr.scraper.loader.agent;

import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.loader.internal.AgentResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Supplier;

public interface Agent {

    Agent header(String name, String value);

    Agent header(String name, String value, Supplier<Boolean> condition);

    Agent proxy(Proxy proxy);

    Mono<AgentResponse> get(String uri, Map<String, ?> params);
}