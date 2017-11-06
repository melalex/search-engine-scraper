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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DuckDuckGoQueryProviderTest {
    private static final String URL = "duckduckgo.com";
    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    private static final String QUERY_VALUE = "query value";

    @Mock
    private QueryContext context;

    private DuckDuckGoQueryProvider testInstance = new DuckDuckGoQueryProvider();

    @Before
    public void setUp() {
        when(context.getWord()).thenReturn(QUERY_VALUE);
    }

    @Test
    public void shouldProvideParams() {
        Map<String, Object> expected = ImmutableMap.<String, Object>builder()
                .put(QUERY, QUERY_VALUE)
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideUrl() {
        assertEquals(URL, testInstance.provideBaseUrl(context));
    }
}