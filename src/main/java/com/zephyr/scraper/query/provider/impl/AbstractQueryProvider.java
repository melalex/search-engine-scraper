package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.internal.Page;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private static final String ROOT = "/";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @NonNull
    private SearchEngine engine;

    @Override
    public Request provide(Keyword keyword) {
        return Request.builder()
                .task(keyword)
                .provider(engine)
                .baseUrl(provideBaseUrl(keyword))
                .uri(provideUri())
                .pages(providePages(keyword))
                .build();
    }

    private List<RequestContext> providePages(ScraperTask task) {
        int first = first();
        int size = pageSize();
        int count = resultCount();

        return IntStream.range(0, (int) Math.ceil(count / size))
                .mapToObj(i -> Page.of(first, i, size, count))
                .map(p -> getPage(task, p))
                .collect(Collectors.toList());
    }

    private RequestContext getPage(ScraperTask task, Page page) {
        return RequestContext.of(providePage(task, page), page.getPage());
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

    protected abstract String provideBaseUrl(ScraperTask task);

    protected abstract Map<String, ?> providePage(ScraperTask task, Page page);
}