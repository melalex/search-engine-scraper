package com.zephyr.scraper.crawler.manager.impl;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.external.SearchEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CrawlingManagerImplTest {

    @Mock
    private CrawlingProvider bingCrawlingProvider;

    @Mock
    private CrawlingProvider googleCrawlingProvider;

    @Mock
    private CrawlingProvider yahooCrawlingProvider;

    @Mock
    private CrawlingProvider duckDuckGoCrawlingProvider;

    @Mock
    private CrawlingProvider yandexCrawlingProvider;

    @InjectMocks
    private CrawlingManagerImpl testInstance;

    @Before
    public void setUp() {
        testInstance.init();
    }

    @Test
    public void shouldManageGoogle() {
        assertEquals(googleCrawlingProvider, testInstance.manage(SearchEngine.GOOGLE));
    }

    @Test
    public void shouldManageBing() {
        assertEquals(bingCrawlingProvider, testInstance.manage(SearchEngine.BING));
    }

    @Test
    public void shouldManageYahoo() {
        assertEquals(yahooCrawlingProvider, testInstance.manage(SearchEngine.YAHOO));
    }

    @Test
    public void shouldManageYandex() {
        assertEquals(yandexCrawlingProvider, testInstance.manage(SearchEngine.YANDEX));
    }

    @Test
    public void shouldManageDuckDuckGo() {
        assertEquals(duckDuckGoCrawlingProvider, testInstance.manage(SearchEngine.DUCKDUCKGO));
    }
}