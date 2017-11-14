package com.zephyr.scraper.crawler.impl;

import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrawlerImpl implements Crawler {

    @Setter(onMethod = @__(@Autowired))
    private CrawlingManager manager;

    @Override
    public List<String> crawl(SearchEngine engine, EngineResponse engineResponse) {
        return manager.manage(engine).provide(engineResponse);
    }
}