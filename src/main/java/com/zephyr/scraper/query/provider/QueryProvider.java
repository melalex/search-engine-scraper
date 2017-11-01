package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.ScraperTask;

@FunctionalInterface
public interface QueryProvider {

    Request provide(ScraperTask task);
}
