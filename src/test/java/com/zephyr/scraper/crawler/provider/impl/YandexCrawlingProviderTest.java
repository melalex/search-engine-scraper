package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.CrawlingUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class YandexCrawlingProviderTest {
    private static final String DUCKDUCKGO_RESPONSE = "duckduckgo-response";
    private static final SearchEngine PROVIDER = SearchEngine.DUCKDUCKGO;

    private YandexCrawlingProvider testInstance = new YandexCrawlingProvider();

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(CrawlingUtils.toResponse(DUCKDUCKGO_RESPONSE, PROVIDER)));
    }

    private List<String> expected() {
        return Collections.emptyList();
    }
}