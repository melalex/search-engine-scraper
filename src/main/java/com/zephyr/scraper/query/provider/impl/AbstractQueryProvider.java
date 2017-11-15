package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.support.PageIterator;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private static final String ROOT = "/";
    private static final int FIRST = 0;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @NonNull
    private SearchEngine engine;

    @Override
    public List<EngineRequest> provide(QueryContext context) {
        return StreamSupport.stream(pageIterator(), false)
                .map(p -> getPage(context, p))
                .collect(Collectors.toList());
    }

    private Spliterator<Page> pageIterator() {
        return PageIterator.of(Page.of(FIRST, first(), pageSize(), resultCount())).asSplitIterator();
    }

    private EngineRequest getPage(QueryContext context, Page page) {
        return EngineRequest.builder()
                .keyword(context.getKeyword())
                .provider(engine)
                .baseUrl(provideBaseUrl(context))
                .uri(provideUri())
                .params(provideParams(context, page))
                .offset(page.getStart())
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

    protected abstract Map<String, List<String>> provideParams(QueryContext context, Page page);
}