package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.utils.MapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.zephyr.scraper.internal.PaginationConstants.FIRST_PAGE;
import static com.zephyr.scraper.internal.PaginationConstants.LAST_PAGE;
import static org.junit.Assert.assertEquals;

public class BingQueryProviderTest {
    private static final String URL = "https://www.bing.com";
    private static final String URI = "/search";
    private static final String QUERY = "q";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    private static final String LANGUAGE = "en";
    private static final String QUERY_VALUE = "query value";
    private static final String QUERY_WITH_LANGUAGE_VALUE = QUERY_VALUE + " language:" + LANGUAGE;

    private QueryContext context;

    private final BingQueryProvider testInstance = new BingQueryProvider();

    @Before
    public void setUp() {
        context = QueryContext.of(DomainUtils.keywordWith(QUERY_VALUE), DomainUtils.ANY_PLACE);
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
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .put(QUERY, QUERY_VALUE)
                .put(COUNT, LAST_PAGE.getPageSize())
                .put(FIRST, LAST_PAGE.getStart())
                .build();

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }

    @Test
    public void shouldProvideParamsForFirstPage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .put(QUERY, QUERY_VALUE)
                .put(COUNT, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideParamsForFirstPageWithLanguage() {
        context = QueryContext.of(DomainUtils.keywordWith(QUERY_VALUE, LANGUAGE), DomainUtils.ANY_PLACE);

        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .put(QUERY, QUERY_WITH_LANGUAGE_VALUE)
                .put(COUNT, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }
}