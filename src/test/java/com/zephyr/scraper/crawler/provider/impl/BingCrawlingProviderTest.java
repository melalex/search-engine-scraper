package com.zephyr.scraper.crawler.provider.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.crawler.fraud.FraudAnalyzer;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.internal.CrawlingUtils;
import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BingCrawlingProviderTest {
    private static final String BING_LINK_SELECTOR = "#b_results > li > h2 > a";
    private static final String BING_RESPONSE = "bing-response.html";

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private FraudAnalyzer fraudAnalyzer;

    @InjectMocks
    private BingCrawlingProvider testInstance;

    @Before
    public void setUp() {
        when(scraperProperties.getScraper(SearchEngine.BING))
                .thenReturn(DomainUtils.engineProperties(BING_LINK_SELECTOR));
    }

    @Test
    public void shouldCrawlBing() {
        assertEquals(expectedBing(), testInstance.provide(CrawlingUtils.toResponse(BING_RESPONSE, SearchEngine.BING)));

        verify(fraudAnalyzer).analyze(eq(SearchEngine.BING), any());
    }


    private List<String> expectedBing() {
        return ImmutableList.of(
                "https://en.wikipedia.org/wiki/Zephyr",
                "https://www.merriam-webster.com/dictionary/zephyr",
                "https://www.getzephyr.com/",
                "http://zephyronline.com/",
                "https://www.zephyranywhere.com/",
                "http://www.dictionary.com/browse/zephyr",
                "https://www.zephyrproject.org/",
                "https://zephyrre.com/",
                "https://www.getzephyr.com/insights",
                "https://marketplace.atlassian.com/plugins/com.thed.zephyr.je/cloud/overview"
        );
    }

}