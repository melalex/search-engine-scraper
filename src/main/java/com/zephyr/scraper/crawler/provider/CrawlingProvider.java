package com.zephyr.scraper.crawler.provider;

import com.zephyr.scraper.domain.Response;

import java.util.List;

@FunctionalInterface
public interface CrawlingProvider {

    List<String> provide(Response response);
}
