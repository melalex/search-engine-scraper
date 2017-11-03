package com.zephyr.scraper.fraud.manager;

import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.fraud.provider.FraudProvider;

@FunctionalInterface
public interface FraudManager {

    FraudProvider manage(SearchEngine searchEngine);
}
