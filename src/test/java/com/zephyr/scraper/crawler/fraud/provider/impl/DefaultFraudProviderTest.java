package com.zephyr.scraper.crawler.fraud.provider.impl;

import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class DefaultFraudProviderTest {
    private final DefaultFraudProvider testInstance = new DefaultFraudProvider();

    @Test
    public void shouldProvide() {
        assertFalse(testInstance.provide(DomainUtils.ANY_DOCUMENT));
    }
}