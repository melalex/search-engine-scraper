package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import com.zephyr.scraper.utils.PaginationUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
    protected String provideBaseUrl(ScraperTask task) {
        return URL;
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, ?> providePage(ScraperTask task, int page, int pageSize) {
        int first = PaginationUtils.startOf(page, pageSize);

        return MapUtils.<String, Object>builder()
                .put(QUERY, task.getWord())
                .put(ENCODING, UTF8)
                .put(COUNT, pageSize)
                .putIfTrue(START, first, PaginationUtils.isNotFirst(first))
                .build();
    }
}