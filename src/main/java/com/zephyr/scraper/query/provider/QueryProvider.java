package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.QueryContext;

import java.util.List;

@FunctionalInterface
public interface QueryProvider {

    List<EngineRequest> provide(QueryContext context);
}
