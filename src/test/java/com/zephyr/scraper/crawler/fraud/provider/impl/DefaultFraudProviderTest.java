package com.zephyr.scraper.crawler.fraud.provider.impl;

import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefaultFraudProviderTest {
    private final DefaultFraudProvider testInstance = new DefaultFraudProvider();

    @Test
    public void shouldProvide() {
        assertTrue(testInstance.provide(DomainUtils.ANY_DOCUMENT));
    }
}