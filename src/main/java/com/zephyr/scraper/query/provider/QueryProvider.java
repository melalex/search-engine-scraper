package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.Request;

import java.util.List;

@FunctionalInterface
public interface QueryProvider {

    List<Request> provide(QueryContext context);
}
