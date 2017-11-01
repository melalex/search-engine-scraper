package com.zephyr.scraper.loader.fraud.manager;

import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.fraud.provider.FraudProvider;

public interface FraudManager {

    FraudProvider manage(SearchEngine searchEngine);
}
