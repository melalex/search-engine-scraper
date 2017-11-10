package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.scraper.crawler.fraud.manager.FraudManager;
import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import com.zephyr.scraper.domain.exceptions.FraudException;
import com.zephyr.scraper.internal.DomainUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FraudAnalyzerImplTest {

    @Mock
    private FraudManager fraudManager;

    @Mock
    private FraudProvider fraudProvider;

    @InjectMocks
    private FraudAnalyzerImpl testInstance;

    @Before
    public void setUp() {
        when(fraudManager.manage(DomainUtils.ANY_PROVIDER)).thenReturn(fraudProvider);
    }

    @Test
    public void shouldPass() {
        when(fraudProvider.provide(DomainUtils.ANY_DOCUMENT)).thenReturn(false);

        testInstance.analyze(DomainUtils.ANY_PROVIDER, DomainUtils.ANY_DOCUMENT);
    }

    @Test(expected = FraudException.class)
    public void shouldFail() {
        when(fraudProvider.provide(DomainUtils.ANY_DOCUMENT)).thenReturn(true);

        testInstance.analyze(DomainUtils.ANY_PROVIDER, DomainUtils.ANY_DOCUMENT);
    }
}