package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class DuckDuckGoCrawlingProviderTest {
    private static final Response DOES_NOT_MATTER = null;

    private DuckDuckGoCrawlingProvider testInstance = new DuckDuckGoCrawlingProvider();

    @Test
    public void shouldProvide() {
        assertNotNull(testInstance.provide(DOES_NOT_MATTER));
    }
}