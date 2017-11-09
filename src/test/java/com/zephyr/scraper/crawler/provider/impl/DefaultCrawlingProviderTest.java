package com.zephyr.scraper.crawler.provider.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.crawler.fraud.FraudAnalyzer;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.CrawlingUtils;
import com.zephyr.scraper.properties.ScraperProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlingProviderTest {
    private static final String GOOGLE_LINK_SELECTOR = "LINK_SELECTOR";
    private static final String BING_LINK_SELECTOR = "LINK_SELECTOR";
    private static final String YAHOO_LINK_SELECTOR = "LINK_SELECTOR";

    private static final String BING_RESPONSE = "bing-response";
    private static final String GOOGLE_RESPONSE = "google-response";
    private static final String YAHOO_RESPONSE = "yahoo-response";

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private FraudAnalyzer fraudAnalyzer;

    @InjectMocks
    private DefaultCrawlingProvider testInstance;

    @Before
    public void setUp() {
        when(scraperProperties.getScraper(SearchEngine.BING)).thenReturn(engineProperties(BING_LINK_SELECTOR));
        when(scraperProperties.getScraper(SearchEngine.GOOGLE)).thenReturn(engineProperties(GOOGLE_LINK_SELECTOR));
        when(scraperProperties.getScraper(SearchEngine.YAHOO)).thenReturn(engineProperties(YAHOO_LINK_SELECTOR));
    }

    @Test
    public void shouldCrawlGoogle() {
        assertEquals(expectedGoogle(), testInstance.provide(CrawlingUtils.toResponse(GOOGLE_RESPONSE, SearchEngine.GOOGLE)));

        verify(fraudAnalyzer).analyze(SearchEngine.GOOGLE, any());
    }

    @Test
    public void shouldCrawlBing() {
        assertEquals(expectedBing(), testInstance.provide(CrawlingUtils.toResponse(BING_RESPONSE, SearchEngine.BING)));

        verify(fraudAnalyzer).analyze(SearchEngine.BING, any());
    }

    @Test
    public void shouldCrawlYahoo() {
        assertEquals(expectedYahoo(), testInstance.provide(CrawlingUtils.toResponse(YAHOO_RESPONSE, SearchEngine.YAHOO)));

        verify(fraudAnalyzer).analyze(SearchEngine.YAHOO, any());
    }

    private ScraperProperties.EngineProperties engineProperties(String linkSelector) {
        ScraperProperties.EngineProperties engineProperties = new ScraperProperties.EngineProperties();
        engineProperties.setLinkSelector(linkSelector);

        return engineProperties;
    }

    private List<String> expectedGoogle() {
        return ImmutableList.of();
    }

    private List<String> expectedBing() {
        return ImmutableList.of();
    }

    private List<String> expectedYahoo() {
        return ImmutableList.of();
    }
}