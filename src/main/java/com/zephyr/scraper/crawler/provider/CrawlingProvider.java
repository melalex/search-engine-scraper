package com.zephyr.scraper.crawler.provider;

import com.zephyr.scraper.domain.EngineResponse;

import java.util.List;

@FunctionalInterface
public interface CrawlingProvider {

    List<String> provide(EngineResponse engineResponse);
}
