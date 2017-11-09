package com.zephyr.scraper.crawler.fraud.manager;

import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import com.zephyr.scraper.domain.external.SearchEngine;

@FunctionalInterface
public interface FraudManager {

    FraudProvider manage(SearchEngine searchEngine);
}
