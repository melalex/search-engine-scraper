package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AjaxHeadersProviderTest {
    private static final String ACCEPT = "*/*";
    private static final String BASE_URL = "BASE_URL";

    private final AjaxHeadersProvider testInstance = new AjaxHeadersProvider();

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(DomainUtils.requestContextWith(BASE_URL)));
    }

    private Map<String, List<String>> expected() {
        return ImmutableMap.<String, List<String>>builder()
                .put(HttpHeaders.ACCEPT, ImmutableList.of(ACCEPT))
                .put(HttpHeaders.HOST, ImmutableList.of(BASE_URL))
                .put(HttpHeaders.ORIGIN, ImmutableList.of(BASE_URL))
                .build();
    }
}