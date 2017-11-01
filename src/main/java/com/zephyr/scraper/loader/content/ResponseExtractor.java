package com.zephyr.scraper.loader.content;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.internal.AgentResponse;

public interface ResponseExtractor {

    PageResponse extract(AgentResponse agentResponse, SearchEngine engine, int number);
}
