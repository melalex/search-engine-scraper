package com.zephyr.scraper.loader.fraud.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.fraud.manager.FraudManager;
import com.zephyr.scraper.loader.fraud.provider.FraudProvider;
import com.zephyr.scraper.loader.fraud.provider.impl.DefaultFraudProvider;
import com.zephyr.scraper.loader.fraud.provider.impl.GoogleFraudProvider;
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
                .put(SearchEngine.YANDEX, new DefaultFraudProvider())
                .put(SearchEngine.DUCKDUCKGO, new DefaultFraudProvider())
                .build();
    }

    @Override
    public FraudProvider manage(SearchEngine searchEngine) {
        return providers.get(searchEngine);
    }
}
