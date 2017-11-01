package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.SearchResult;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractCrawlingProvider implements CrawlingProvider {

    @Override
    public SearchResult provide(Response response) {
        return SearchResult.builder()
                .task(response.getTask())
                .provider(response.getProvider())
                .timestamp(LocalDateTime.now())
                .links(parse(response))
                .build();
    }

    protected abstract List<String> parse(Response response);
}
