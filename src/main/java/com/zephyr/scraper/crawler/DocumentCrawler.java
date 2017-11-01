package com.zephyr.scraper.crawler;

import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.SearchResult;

@FunctionalInterface
public interface DocumentCrawler {

    SearchResult crawl(Response document);
}
