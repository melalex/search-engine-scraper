package com.zephyr.scraper.crawler.fraud.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.crawler.fraud.manager.FraudManager;
import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import com.zephyr.scraper.crawler.fraud.provider.impl.DefaultFraudProvider;
import com.zephyr.scraper.crawler.fraud.provider.impl.GoogleFraudProvider;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class FraudManagerImpl implements FraudManager {
    private Map<SearchEngine, FraudProvider> providers;

    @PostConstruct
    public void init() {
        providers = ImmutableMap.<SearchEngine, FraudProvider>builder()
                .put(SearchEngine.GOOGLE, new GoogleFraudProvider())
                .put(SearchEngine.BING, new DefaultFraudProvider())
                .put(SearchEngine.YAHOO, new DefaultFraudProvider())
                .build();
    }

    @Override
    public FraudProvider manage(SearchEngine searchEngine) {
        return MapUtils.getOrThrow(providers, searchEngine);
    }
}
