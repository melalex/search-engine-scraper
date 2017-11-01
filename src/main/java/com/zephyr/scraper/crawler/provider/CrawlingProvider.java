package com.zephyr.scraper.crawler.provider;

import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.SearchResult;

public interface CrawlingProvider {

    SearchResult provide(Response response);
}
