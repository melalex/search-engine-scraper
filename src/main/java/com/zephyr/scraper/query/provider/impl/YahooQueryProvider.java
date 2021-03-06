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
@ConditionalOnProperty(name = "scraper.yahoo.enabled", havingValue = "true")
public class YahooQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://search.yahoo.com";
    private static final String URI = "/search";
    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    public YahooQueryProvider() {
        super(SearchEngine.YAHOO);
    }

    @Override
    protected String provideBaseUrl(QueryContext context) {
        return URL;
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryContext context, Page page) {
        return MapUtils.<String, Object>multiValueMapBuilder()
                .put(QUERY, context.getWord())
                .put(ENCODING, UTF8)
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}