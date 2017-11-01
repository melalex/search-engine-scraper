package com.zephyr.scraper.loader.agent;

import com.zephyr.scraper.loader.internal.RequestContext;

@FunctionalInterface
public interface AgentFactory {

    Agent create(RequestContext context);
}