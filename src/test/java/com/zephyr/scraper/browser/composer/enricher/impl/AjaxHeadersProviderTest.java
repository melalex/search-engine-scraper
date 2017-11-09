package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.domain.RequestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AjaxHeadersProviderTest {
    private static final String ACCEPT = "*/*";
    private static final String BASE_URL = "BASE_URL";

    @Mock
    private RequestContext context;

    private final AjaxHeadersProvider testInstance = new AjaxHeadersProvider();

    @Before
    public void setUp() {
        when(context.getBaseUrl()).thenReturn(BASE_URL);
    }

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(context));
    }

    private Map<String, List<String>> expected() {
        return ImmutableMap.<String, List<String>>builder()
                .put(HttpHeaders.ACCEPT, ImmutableList.of(ACCEPT))
                .put(HttpHeaders.HOST, ImmutableList.of(BASE_URL))
                .put(HttpHeaders.ORIGIN, ImmutableList.of(BASE_URL))
                .build();
    }
}