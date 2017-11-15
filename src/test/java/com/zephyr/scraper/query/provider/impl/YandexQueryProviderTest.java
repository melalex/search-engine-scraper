package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.PlaceDto;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.utils.MapUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.zephyr.scraper.internal.PaginationConstants.FIRST_PAGE;
import static com.zephyr.scraper.internal.PaginationConstants.LAST_PAGE;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class YandexQueryProviderTest {
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    private static final String LOCAL_YANDEX = "https://yandex.ua";
    private static final String DEFAULT_YANDEX = "https://yandex.ru";
    private static final String QUERY_VALUE = "query value";

    private QueryContext context;

    private final YandexQueryProvider testInstance = new YandexQueryProvider();

    @Before
    public void setUp() {
        context = QueryContext.of(DomainUtils.keywordWith(QUERY_VALUE), createPlace());
    }

    @Test
    public void shouldProvideBaseUrl() {
        assertEquals(LOCAL_YANDEX, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideDefaultUrl() {
        context.getCountry().setLocaleYandex(null);

        assertEquals(DEFAULT_YANDEX, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideParamsForFirstPage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .put(QUERY, context.getWord())
                .put(COUNT, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideParamsForLastPage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .put(QUERY, QUERY_VALUE)
                .put(COUNT, LAST_PAGE.getPageSize())
                .put(START, LAST_PAGE.getStart())
                .build();

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }

    private PlaceDto createPlace() {
        PlaceDto place = new PlaceDto();
        place.setCountry(DomainUtils.countryWithYandex(LOCAL_YANDEX));

        return place;
    }
}