package com.zephyr.scraper.crawler.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.external.SearchEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerImplTest {
    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;
    private static final EngineResponse DOES_NOT_MATTER = null;

    @Mock
    private CrawlingManager crawlingManager;

    @Mock
    private CrawlingProvider crawlingProvider;

    @InjectMocks
    private CrawlerImpl testInstance;

    @Before
    public void setUp() {
        when(crawlingManager.manage(PROVIDER)).thenReturn(crawlingProvider);
        when(crawlingProvider.provide(DOES_NOT_MATTER)).thenReturn(result());
    }

    @Test
    public void shouldCrawl() {
        assertEquals(result(), testInstance.crawl(PROVIDER, DOES_NOT_MATTER));
    }

    private List<String> result() {
        return ImmutableList.of("result");
    }
}