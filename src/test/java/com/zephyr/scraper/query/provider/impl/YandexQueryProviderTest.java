package com.zephyr.scraper.query.provider.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.CountryDto;
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
public class YandexQueryProviderTest {
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    private static final String LOCAL_YANDEX = "yandex.ua";
    private static final String DEFAULT_YANDEX = "yandex.ru";
    private static final String QUERY_VALUE = "query value";

    @Mock
    private CountryDto country;

    @Mock
    private QueryContext context;

    private YandexQueryProvider testInstance = new YandexQueryProvider();

    @Before
    public void setUp() {
        when(context.getWord()).thenReturn(QUERY_VALUE);
        when(context.getCountry()).thenReturn(country);
        when(country.getLocaleYandex()).thenReturn(LOCAL_YANDEX);
    }

    @Test
    public void shouldProvideBaseUrl() {
        assertEquals(LOCAL_YANDEX, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideDefaultUrl() {
        when(country.getLocaleYandex()).thenReturn(null);

        assertEquals(DEFAULT_YANDEX, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideParamsForFirstPage() {
        Map<String, Object> expected = ImmutableMap.<String, Object>builder()
                .put(QUERY, context.getWord())
                .put(COUNT, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideParamsForLastPage() {
        Map<String, Object> expected = ImmutableMap.<String, Object>builder()
                .put(QUERY, QUERY_VALUE)
                .put(COUNT, LAST_PAGE.getPageSize())
                .put(START, LAST_PAGE.getStart())
                .build();

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }
}