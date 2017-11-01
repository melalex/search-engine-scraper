package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import com.zephyr.scraper.utils.PaginationUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "scraper.yandex.enabled", havingValue = "true")
public class YandexQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://yandex.ru";
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    public YandexQueryProvider() {
        super(SearchEngine.YANDEX);
    }

    @Override
    protected String provideBaseUrl(ScraperTask task) {
        return URL;
    }

    @Override
    protected Map<String, ?> providePage(ScraperTask task, int page, int pageSize) {
        int first = PaginationUtils.startOfZeroBased(page, pageSize);

        return MapUtils.<String, Object>builder()
                .put(QUERY, task.getWord())
                .put(COUNT, pageSize)
                .putIfTrue(START, first, PaginationUtils.isNotFirstZeroBased(first))
                .build();
    }
}
