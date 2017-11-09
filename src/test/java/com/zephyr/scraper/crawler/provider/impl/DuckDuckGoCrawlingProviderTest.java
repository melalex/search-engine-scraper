package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.CrawlingUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DuckDuckGoCrawlingProviderTest {
    private static final String YANDEX_RESPONSE = "yandex-response";
    private static final SearchEngine PROVIDER = SearchEngine.YANDEX;

    private DuckDuckGoCrawlingProvider testInstance = new DuckDuckGoCrawlingProvider();

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(CrawlingUtils.toResponse(YANDEX_RESPONSE, PROVIDER)));
    }

    private List<String> expected() {
        return Collections.emptyList();
    }
}