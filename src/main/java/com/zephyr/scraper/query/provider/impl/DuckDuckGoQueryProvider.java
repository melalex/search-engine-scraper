package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "scraper.duckduckgo.enabled", havingValue = "true")
public class DuckDuckGoQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://duckduckgo.com";
    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    public DuckDuckGoQueryProvider() {
        super(SearchEngine.DUCKDUCKGO);
    }

    @Override
    protected String provideBaseUrl(QueryContext context) {
        return URL;
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryContext context, Page page) {
        return MapUtils.multiValueMapBuilder()
                .put(QUERY, context.getWord())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}