package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.Response;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YandexCrawlingProviderTest {
    private static final Response DOES_NOT_MATTER = null;

    private YandexCrawlingProvider testInstance = new YandexCrawlingProvider();

    @Test
    public void shouldProvide() {
        assertNotNull(testInstance.provide(DOES_NOT_MATTER));
    }
}