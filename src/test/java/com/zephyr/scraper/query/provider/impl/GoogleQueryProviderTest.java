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

import static com.zephyr.scraper.internal.PaginationConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoogleQueryProviderTest {
    private static final String URI = "/search";
    private static final String LANGUAGE = "lr";
    private static final String INTERFACE = "hl";
    private static final String AD_TEST = "adtest";
    private static final String ON = "on";
    private static final String LOCATION = "uule";
    private static final String GLP = "glp";
    private static final String ONE = "1";
    private static final String PARENT = "tci";
    private static final String SAFE = "safe";
    private static final String IMAGE = "image";
    private static final String QUERY = "q";
    private static final String START = "start";
    private static final String NUMBER = "num";

    private static final String QUERY_VALUE = "query value";
    private static final String DEFAULT_LOCAL_GOOGLE = "www.google.com";
    private static final String LOCAL_GOOGLE = "www.google.com.af";
    private static final String UULE = "UULE";
    private static final String LANGUAGE_ISO = "en";
    private static final long PARENT_LOCATION = 1488;

    @Mock
    private CountryDto country;

    @Mock
    private QueryContext context;

    private GoogleQueryProvider testInstance = new GoogleQueryProvider();

    @Before
    public void setUp() {
        when(country.getLocaleGoogle()).thenReturn(LOCAL_GOOGLE);
        when(context.getCountry()).thenReturn(country);
        when(context.getWord()).thenReturn(QUERY_VALUE);
        when(context.getParent()).thenReturn(PARENT_LOCATION);
        when(context.getLocation()).thenReturn(UULE);
        when(context.getLanguageIso()).thenReturn(LANGUAGE_ISO);
    }

    @Test
    public void shouldProvideBaseUrl() {
        assertEquals(LOCAL_GOOGLE, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideDefaultUrl() {
        when(country.getLocaleGoogle()).thenReturn(null);

        assertEquals(DEFAULT_LOCAL_GOOGLE, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideUri() {
        assertEquals(URI, testInstance.provideUri());
    }

    @Test
    public void shouldProvideForLastPage() {
        Map<String, ?> expected = ImmutableMap.<String, Object>builder()
                .putAll(defaultParams())
                .put(NUMBER, LAST_PAGE.getPageSize())
                .put(START, LAST_PAGE.getStart())
                .build();

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }

    @Test
    public void shouldProvideForFirstPage() {
        Map<String, ?> expected = ImmutableMap.<String, Object>builder()
                .putAll(defaultParams())
                .put(NUMBER, FIRST_PAGE.getPageSize())
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideForFirstPageWithLanguage() {
        Map<String, ?> expected = ImmutableMap.<String, Object>builder()
                .putAll(defaultParams())
                .put(NUMBER, FIRST_PAGE.getPageSize())
                .put(LANGUAGE, "lang_" + LANGUAGE_ISO)
                .put(INTERFACE, LANGUAGE_ISO)
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    private Map<String, ?> defaultParams() {
        return ImmutableMap.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, context.getWord())
                .put(PARENT, "g:" + PARENT_LOCATION)
                .put(LOCATION, context.getLocation())
                .build();
    }
}