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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class YahooCrawlingProviderTest {
    private static final String YAHOO_LINK_SELECTOR = "#web > ol > li > div > div > div > span";
    private static final String YAHOO_RESPONSE = "yahoo-response.html";

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private FraudAnalyzer fraudAnalyzer;

    @InjectMocks
    private YahooCrawlingProvider testInstance;

    @Before
    public void setUp() {
        when(scraperProperties.getScraper(SearchEngine.YAHOO))
                .thenReturn(DomainUtils.engineProperties(YAHOO_LINK_SELECTOR));
    }

    @Test
    public void shouldCrawlYahoo() {
        assertEquals(expectedYahoo(), testInstance.provide(CrawlingUtils.toResponse(YAHOO_RESPONSE, SearchEngine.YAHOO)));

        verify(fraudAnalyzer).analyze(eq(SearchEngine.YAHOO), any());
    }


    private List<String> expectedYahoo() {
        return ImmutableList.of(
                "zephyronline.com",
                "www.merriam-webster.com/dictionary/zephyr",
                "en.wikipedia.org/wiki/Zephyr",
                "www.dictionary.com/browse/zephyr",
                "www.getzephyr.com",
                "www.thefreedictionary.com/zephyr",
                "www.zephyrproject.org",
                "www.amtrak.com/california-zephyr-train",
                "zephyrre.com",
                "www.zhats.com"
        );
    }

}