package com.zephyr.scraper.crawler.fraud.manager.impl;

import com.zephyr.scraper.crawler.fraud.provider.impl.DefaultFraudProvider;
import com.zephyr.scraper.crawler.fraud.provider.impl.GoogleFraudProvider;
import com.zephyr.scraper.domain.external.SearchEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FraudManagerImplTest {
    private final FraudManagerImpl testInstance = new FraudManagerImpl();

    @Before
    public void setUp() {
        testInstance.init();
    }

    @Test
    public void shouldManageGoogle() {
        assertTrue(testInstance.manage(SearchEngine.GOOGLE) instanceof GoogleFraudProvider);
    }

    @Test
    public void shouldManageBing() {
        assertTrue(testInstance.manage(SearchEngine.BING) instanceof DefaultFraudProvider);
    }

    @Test
    public void shouldManageYahoo() {
        assertTrue(testInstance.manage(SearchEngine.YAHOO) instanceof DefaultFraudProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotManageYandex() {
        testInstance.manage(SearchEngine.YANDEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotManageDuckDuckGo() {
        testInstance.manage(SearchEngine.DUCKDUCKGO);
    }
}