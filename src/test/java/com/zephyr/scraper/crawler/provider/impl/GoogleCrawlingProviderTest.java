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
public class GoogleCrawlingProviderTest {
    private static final String GOOGLE_LINK_SELECTOR = "#rso > div > div > div> div > div > div > div > div.f.kv._SWb > cite";
    private static final String GOOGLE_RESPONSE = "google-response.html";

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private FraudAnalyzer fraudAnalyzer;

    @InjectMocks
    private GoogleCrawlingProvider testInstance;

    @Before
    public void setUp() {
        when(scraperProperties.getScraper(SearchEngine.GOOGLE))
                .thenReturn(DomainUtils.engineProperties(GOOGLE_LINK_SELECTOR));
    }

    @Test
    public void shouldCrawlGoogle() {
        assertEquals(expectedGoogle(), testInstance.provide(CrawlingUtils.toResponse(GOOGLE_RESPONSE, SearchEngine.GOOGLE)));

        verify(fraudAnalyzer).analyze(eq(SearchEngine.GOOGLE), any());
    }

    private List<String> expectedGoogle() {
        return ImmutableList.of(
                "https://www.getzephyr.com/",
                "https://www.zephyrproject.org/",
                "https://marketplace.atlassian.com/plugins/com.thed.zephyr.je/cloud/overview",
                "https://en.wikipedia.org/wiki/Zephyr",
                "https://en.wikipedia.org/wiki/Zephyr_(protocol)",
                "https://www.merriam-webster.com/dictionary/zephyr",
                "www.dictionary.com/browse/zephyr",
                "https://www.crunchbase.com/organization/zephyr",
                "leagueoflegends.wikia.com/wiki/Zephyr"
        );
    }

}