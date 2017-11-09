package com.zephyr.scraper.query.provider.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.domain.QueryContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static com.zephyr.scraper.internal.PaginationConstants.FIRST_PAGE;
import static com.zephyr.scraper.internal.PaginationConstants.LAST_PAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class YahooQueryProviderTest {
    private static final String URL = "search.yahoo.com";
    private static final String URI = "/search";
    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    private static final String QUERY_VALUE = "query value";

    @Mock
    private QueryContext context;

    private final YahooQueryProvider testInstance = new YahooQueryProvider();

    @Before
    public void setUp() {
        when(context.getWord()).thenReturn(QUERY_VALUE);
    }

    @Test
    public void shouldProvideBaseUrl() {
        assertEquals(URL, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideUri() {
        assertEquals(URI, testInstance.provideUri());
    }

    @Test
    public void shouldProvideParamsForLastPage() {
        Map<String, Object> expected = ImmutableMap.<String, Object>builder()
                .put(QUERY, context.getWord())
                .put(ENCODING, UTF8)
                .put(COUNT, LAST_PAGE.getPageSize())
                .put(START, LAST_PAGE.getStart())
                .build();

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }

    @Test
    public void shouldProvideParamsForFirstPage() {
        Map<String, Object> expected = ImmutableMap.<String, Object>builder()
                .put(QUERY, QUERY_VALUE)
                .put(ENCODING, UTF8)
                .put(COUNT, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }
}