package com.zephyr.scraper.crawler.fraud.provider.impl;

import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefaultFraudProviderTest {
    private static final Document DOES_NOT_MATTER = null;

    private final DefaultFraudProvider testInstance = new DefaultFraudProvider();

    @Test
    public void shouldProvide() {
        assertTrue(testInstance.provide(DOES_NOT_MATTER));
    }
}