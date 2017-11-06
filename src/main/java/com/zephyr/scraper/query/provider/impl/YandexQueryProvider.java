package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "scraper.yandex.enabled", havingValue = "true")
public class YandexQueryProvider extends AbstractQueryProvider {
    private static final String URL = "yandex.ru";
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    public YandexQueryProvider() {
        super(SearchEngine.YANDEX);
    }

    @Override
    protected String provideBaseUrl(QueryContext context) {
        return Optional.ofNullable(context.getCountry().getLocaleYandex()).orElse(URL);
    }

    @Override
    protected Map<String, ?> provideParams(QueryContext context, Page page) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, context.getWord())
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}