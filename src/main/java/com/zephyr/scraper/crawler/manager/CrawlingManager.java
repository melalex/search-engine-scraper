package com.zephyr.scraper.crawler.manager;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.external.SearchEngine;

public interface CrawlingManager {

    CrawlingProvider manage(SearchEngine engine);
}
