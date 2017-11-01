package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.query.internal.Page;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@ConditionalOnProperty(name = "scraper.google.enabled", havingValue = "true")
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String URI = "/search";
    private static final String LANGUAGE = "lr";
    private static final String INTERFACE = "hl";
    private static final String AD_TEST = "adtest";
    private static final String ON = "on";
    private static final String LOCATION = "uule";
    private static final String GLP = "glp";
    private static final String ONE = "1";
    private static final String PARENT = "tci";
    private static final String SAFE = "safe";
    private static final String IMAGE = "image";
    private static final String QUERY = "q";
    private static final String START = "start";
    private static final String NUMBER = "num";

    public GoogleQueryProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    protected String provideBaseUrl(ScraperTask task) {
        return "https://www." + task.getLocaleGoogle();
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, ?> providePage(ScraperTask task, Page page) {
        return MapUtils.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, task.getWord())
                .put(NUMBER, page.getPageSize())
                .put(PARENT, getParent(task))
                .put(LOCATION, task.getLocation())
                .put(INTERFACE, task.getLanguageIso())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .putIfNotNull(LANGUAGE, getLanguage(task))
                .build();
    }

    private String getParent(ScraperTask task) {
        return "g:" + task.getParent();
    }

    private String getLanguage(ScraperTask task) {
        String iso = task.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + task.getLanguageIso() : null;
    }
}