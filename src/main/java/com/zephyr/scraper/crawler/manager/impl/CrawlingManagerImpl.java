package com.zephyr.scraper.crawler.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CrawlingManagerImpl implements CrawlingManager {
    private Map<SearchEngine, CrawlingProvider> providers;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider bingCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider googleCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yahooCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider duckDuckGoCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yandexCrawlingProvider;

    @PostConstruct
    public void init() {
        providers = ImmutableMap.<SearchEngine, CrawlingProvider>builder()
                .put(SearchEngine.GOOGLE, googleCrawlingProvider)
                .put(SearchEngine.BING, bingCrawlingProvider)
                .put(SearchEngine.YAHOO, yahooCrawlingProvider)
                .put(SearchEngine.DUCKDUCKGO, duckDuckGoCrawlingProvider)
                .put(SearchEngine.YANDEX, yandexCrawlingProvider)
                .build();
    }

    @Override
    public CrawlingProvider manage(SearchEngine engine) {
        return providers.get(engine);
    }
}