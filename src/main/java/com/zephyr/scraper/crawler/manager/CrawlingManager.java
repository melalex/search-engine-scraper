package com.zephyr.scraper.crawler.manager;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.external.SearchEngine;

@FunctionalInterface
public interface CrawlingManager {

    CrawlingProvider manage(SearchEngine engine);
}
