package com.zephyr.scraper.browser.composer.managers.impl;

import com.google.common.collect.ImmutableSet;
import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.browser.composer.managers.HeadersManager;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class EngineSpecificManager implements HeadersManager {
    private static final Set<SearchEngine> SINGLE_PAGE_ENGINES = ImmutableSet.of(SearchEngine.DUCKDUCKGO, SearchEngine.YANDEX);

    @Setter(onMethod = @__(@Autowired))
    private HeadersProvider ajaxHeadersProvider;

    @Setter(onMethod = @__(@Autowired))
    private HeadersProvider htmlHeadersProvider;

    @Override
    public Map<String, List<String>> manage(RequestContext context) {
        return SINGLE_PAGE_ENGINES.contains(context.getProvider())
                ? ajaxHeadersProvider.provide(context)
                : htmlHeadersProvider.provide(context);
    }
}