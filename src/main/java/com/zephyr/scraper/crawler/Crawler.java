package com.zephyr.scraper.crawler;

import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.external.SearchEngine;

import java.util.List;

@FunctionalInterface
public interface Crawler {

    List<String> crawl(SearchEngine engine, Response response);
}
