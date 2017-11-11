package com.zephyr.scraper.crawler.fraud.provider.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoogleFraudProviderTest {
    private static final String SELECTOR = "#captcha";

    @Mock
    private Document document;

    @Mock
    private Elements elements;

    private final GoogleFraudProvider testInstance = new GoogleFraudProvider();

    @Before
    public void setUp() {
        when(document.select(SELECTOR)).thenReturn(elements);
    }

    @Test
    public void shouldProvideTrue() {
        when(elements.first()).thenReturn(mock(Element.class));

        assertTrue(testInstance.provide(document));
    }

    @Test
    public void shouldProvideFalse() {
        when(elements.first()).thenReturn(null);

        assertFalse(testInstance.provide(document));
    }
}