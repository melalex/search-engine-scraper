package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.fraud.FraudAnalyzer;
import com.zephyr.scraper.properties.ScraperProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlingProviderTest {
    private static final String LINK_SELECTOR = "LINK_SELECTOR";
    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;

    @Mock
    private ScraperProperties.EngineProperties engineProperties;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private FraudAnalyzer fraudAnalyzer;

    @InjectMocks
    private DefaultCrawlingProvider testInstance;

    @Before
    public void setUp() {
        when(engineProperties.getLinkSelector()).thenReturn(LINK_SELECTOR);
        when(scraperProperties.getScraper(PROVIDER)).thenReturn(engineProperties);
    }

    @Test
    public void shouldProvide() {
        testInstance.provide(response());
    }

    private Response response() {
        return Response.of(Map.of(), "", PROVIDER);
    }
}