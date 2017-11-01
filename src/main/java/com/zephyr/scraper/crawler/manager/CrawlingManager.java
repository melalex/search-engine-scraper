package com.zephyr.scraper.crawler.manager;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.SearchEngine;

public interface CrawlingManager {

    CrawlingProvider manage(SearchEngine engine);
}
