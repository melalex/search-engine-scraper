package com.zephyr.scraper.browser.composer.managers.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EngineSpecificManagerTest {
    private static final String FIRST_KEY = "FIRST_KEY";
    private static final List<String> FIRST_VALUE = ImmutableList.of("FIRST_VALUE");
    private static final String SECOND_KEY = "SECOND_KEY";
    private static final List<String> SECOND_VALUE = ImmutableList.of("SECOND_VALUE");

    private static final SearchEngine AJAX_ENGINE = SearchEngine.DUCKDUCKGO;
    private static final SearchEngine HTML_ENGINE = SearchEngine.GOOGLE;

    @Mock
    private RequestContext ajaxRequestContext;

    @Mock
    private RequestContext htmlRequestContext;

    @Mock
    private HeadersProvider ajaxHeadersProvider;

    @Mock
    private HeadersProvider htmlHeadersProvider;

    @InjectMocks
    private EngineSpecificManager testInstance;

    @Before
    public void setUp() {
        when(ajaxRequestContext.getProvider()).thenReturn(AJAX_ENGINE);
        when(htmlRequestContext.getProvider()).thenReturn(HTML_ENGINE);

        when(ajaxHeadersProvider.provide(ajaxRequestContext)).thenReturn(expectedAjax());
        when(htmlHeadersProvider.provide(htmlRequestContext)).thenReturn(expectedHtml());
    }

    @Test
    public void shouldManageAjax() {
        assertEquals(expectedAjax(), testInstance.manage(ajaxRequestContext));
    }

    @Test
    public void shouldManageHtml() {
        assertEquals(expectedHtml(), testInstance.manage(htmlRequestContext));
    }

    private Map<String, List<String>> expectedAjax() {
        return ImmutableMap.<String, List<String>>builder()
                .put(FIRST_KEY, FIRST_VALUE)
                .build();
    }

    private Map<String, List<String>> expectedHtml() {
        return ImmutableMap.<String, List<String>>builder()
                .put(SECOND_KEY, SECOND_VALUE)
                .build();
    }
}