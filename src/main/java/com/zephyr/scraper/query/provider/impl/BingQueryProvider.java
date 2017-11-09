package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@ConditionalOnProperty(name = "scraper.bing.enabled", havingValue = "true")
public class BingQueryProvider extends AbstractQueryProvider {
    private static final String URL = "www.bing.com";
    private static final String URI = "/search";
    private static final String QUERY = "q";
    private static final String LANGUAGE = " language:";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    public BingQueryProvider() {
        super(SearchEngine.BING);
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
    protected Map<String, ?> provideParams(QueryContext context, Page page) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, getQuery(context))
                .put(COUNT, page.getPageSize())
                .putIfTrue(FIRST, page.getStart(), page.isNotFirst())
                .build();
    }

    private String getQuery(QueryContext context) {
        return context.getWord() + getLanguage(context);
    }

    private String getLanguage(QueryContext context) {
        return isNotBlank(context.getLanguageIso())
                ? LANGUAGE + context.getLanguageIso()
                : StringUtils.EMPTY;
    }
}
