package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HtmlHeadersProviderTest {
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String TRUE = "1";

    private final HtmlHeadersProvider testInstance = new HtmlHeadersProvider();

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(DomainUtils.ANY_REQUEST_CONTEXT));
    }

    private Map<String, List<String>> expected() {
        return ImmutableMap.<String, List<String>>builder()
                .put(HttpHeaders.ACCEPT, ImmutableList.of(ACCEPT))
                .put(UPGRADE_INSECURE_REQUESTS, ImmutableList.of(TRUE))
                .build();
    }
}