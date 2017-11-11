package com.zephyr.scraper.source.impl;

import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Test;

public class DefaultProxySourceTest {
    private static final String PROXY_ID = "PROXY_ID";

    private final DefaultProxySource testInstance = new DefaultProxySource();

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionWhenReserve() {
        testInstance.reserve(DomainUtils.ANY_PROVIDER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionWhenReport() {
        testInstance.report(PROXY_ID, DomainUtils.ANY_PROVIDER);
    }
}