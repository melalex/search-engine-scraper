package com.zephyr.scraper.query.provider.impl;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.Keyword;
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
    private static final String DEFAULT_LOCAL_GOOGLE = "https://www.google.com";
    private static final String LOCAL_GOOGLE = "https://www.google.com.af";
    private static final String UULE = "UULE";
    private static final String LANGUAGE_ISO = "en";
    private static final long PARENT_LOCATION = 1488;

    private QueryContext context;

    private final GoogleQueryProvider testInstance = new GoogleQueryProvider();

    @Before
    public void setUp() {
        context = QueryContext.of(createKeyword(), createPlace());
    }

    @Test
    public void shouldProvideBaseUrl() {
        assertEquals(LOCAL_GOOGLE, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideDefaultUrl() {
        context.getCountry().setLocaleGoogle(null);

        assertEquals(DEFAULT_LOCAL_GOOGLE, testInstance.provideBaseUrl(context));
    }

    @Test
    public void shouldProvideUri() {
        assertEquals(URI, testInstance.provideUri());
    }

    @Test
    public void shouldProvideForLastPage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .putAll(defaultParams())
                .put(NUMBER, LAST_PAGE.getPageSize())
                .put(START, LAST_PAGE.getStart())
                .build();

        context.getKeyword().setLanguageIso(null);

        assertEquals(expected, testInstance.provideParams(context, LAST_PAGE));
    }

    @Test
    public void shouldProvideForFirstPage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .putAll(defaultParams())
                .put(NUMBER, FIRST_PAGE.getPageSize())
                .build();

        context.getKeyword().setLanguageIso(null);

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    @Test
    public void shouldProvideForFirstPageWithLanguage() {
        Map<String, List<String>> expected = MapUtils.multiValueMapBuilder()
                .putAll(defaultParams())
                .put(NUMBER, FIRST_PAGE.getPageSize())
                .put(LANGUAGE, "lang_" + LANGUAGE_ISO)
                .put(INTERFACE, LANGUAGE_ISO)
                .build();

        assertEquals(expected, testInstance.provideParams(context, FIRST_PAGE));
    }

    private Map<String, List<String>> defaultParams() {
        return MapUtils.multiValueMapBuilder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, context.getWord())
                .put(PARENT, "g:" + PARENT_LOCATION)
                .put(LOCATION, context.getLocation())
                .build();
    }

    private Keyword createKeyword() {
        Keyword keyword = new Keyword();
        keyword.setWord(QUERY_VALUE);
        keyword.setLanguageIso(LANGUAGE_ISO);

        return keyword;
    }

    private PlaceDto createPlace() {
        PlaceDto place = new PlaceDto();
        place.setCountry(DomainUtils.countryWithGoogle(LOCAL_GOOGLE));
        place.setParent(PARENT_LOCATION);
        place.setLocation(UULE);

        return place;
    }
}