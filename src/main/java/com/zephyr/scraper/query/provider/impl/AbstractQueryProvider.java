package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private static final String ROOT = "/";
    private static final int FIRST = 0;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @NonNull
    private SearchEngine engine;

    @Override
    public List<Request> provide(QueryContext context) {
        return Stream.iterate(Page.of(first(), FIRST, pageSize(), resultCount()), Page::hasNextPage, Page::getNextPage)
                .map(p -> getPage(context, p))
                .collect(Collectors.toList());
    }

    private Request getPage(QueryContext context, Page page) {
        return Request.builder()
                .keyword(context.getKeyword())
                .provider(engine)
                .baseUrl(provideBaseUrl(context))
                .uri(provideUri())
                .params(provideParams(context, page))
                .build();
    }

    private int first() {
        return properties.getScraper(engine).getFirst();
    }

    private int pageSize() {
        return properties.getScraper(engine).getPageSize();
    }

    private int resultCount() {
        return properties.getScraper(engine).getResultCount();
    }

    protected String provideUri() {
        return ROOT;
    }

    protected abstract String provideBaseUrl(QueryContext context);

    protected abstract Map<String, ?> provideParams(QueryContext context, Page page);
}