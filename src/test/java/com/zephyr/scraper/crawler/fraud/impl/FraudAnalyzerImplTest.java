package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.scraper.domain.exceptions.FraudException;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.crawler.fraud.manager.FraudManager;
import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FraudAnalyzerImplTest {
    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;
    private static final Document DOES_NOT_MATTER = null;

    @Mock
    private FraudManager fraudManager;

    @Mock
    private FraudProvider fraudProvider;

    @InjectMocks
    private FraudAnalyzerImpl testInstance;

    @Before
    public void setUp() {
        when(fraudManager.manage(SearchEngine.GOOGLE)).thenReturn(fraudProvider);
    }

    @Test
    public void shouldPass() {
        when(fraudProvider.provide(DOES_NOT_MATTER)).thenReturn(false);

        testInstance.analyze(PROVIDER, DOES_NOT_MATTER);
    }

    @Test(expected = FraudException.class)
    public void shouldFail() {
        when(fraudProvider.provide(DOES_NOT_MATTER)).thenReturn(true);

        testInstance.analyze(PROVIDER, DOES_NOT_MATTER);
    }
}