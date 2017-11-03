package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.QueryContext;

import java.util.List;

@FunctionalInterface
public interface QueryProvider {

    List<Request> provide(QueryContext context);
}
