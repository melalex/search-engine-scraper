package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.Keyword;

@FunctionalInterface
public interface QueryProvider {

    Request provide(Keyword keyword);
}
