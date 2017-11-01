package com.zephyr.scraper.crawler.impl;

import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.SearchResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentCrawlerImpl implements DocumentCrawler {

    @Setter(onMethod = @__(@Autowired))
    private CrawlingManager manager;

    @Override
    public SearchResult crawl(Response document) {
        return manager.manage(document.getProvider()).provide(document);
    }
}