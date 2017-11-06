package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "scraper.google.enabled", havingValue = "true")
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String URL = "www.google.com";
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
    protected String provideBaseUrl(QueryContext context) {
        return Optional.ofNullable(context.getCountry().getLocaleGoogle()).orElse(URL);
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, ?> provideParams(QueryContext context, Page page) {
        return MapUtils.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, context.getWord())
                .put(NUMBER, page.getPageSize())
                .put(PARENT, getParent(context))
                .put(LOCATION, context.getLocation())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .putIfNotNull(INTERFACE, context.getLanguageIso())
                .putIfNotNull(LANGUAGE, getLanguage(context))
                .build();
    }

    private String getParent(QueryContext context) {
        return "g:" + context.getParent();
    }

    private String getLanguage(QueryContext context) {
        String iso = context.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + context.getLanguageIso() : null;
    }
}