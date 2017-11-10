package com.zephyr.scraper.browser.composer.managers.impl;

import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EngineSpecificManagerTest {
    private static final SearchEngine AJAX_ENGINE = SearchEngine.DUCKDUCKGO;
    private static final SearchEngine HTML_ENGINE = SearchEngine.GOOGLE;

    @Mock
    private HeadersProvider ajaxHeadersProvider;

    @Mock
    private HeadersProvider htmlHeadersProvider;

    @InjectMocks
    private EngineSpecificManager testInstance;

    @Test
    public void shouldManageAjax() {
        RequestContext context = DomainUtils.requestContextWithEngine(AJAX_ENGINE);

        testInstance.manage(context);

        verify(ajaxHeadersProvider).provide(context);
    }

    @Test
    public void shouldManageHtml() {
        RequestContext context = DomainUtils.requestContextWithEngine(HTML_ENGINE);

        testInstance.manage(context);

        verify(htmlHeadersProvider).provide(context);
    }
}