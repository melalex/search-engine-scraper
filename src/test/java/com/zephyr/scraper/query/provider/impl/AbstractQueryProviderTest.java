package com.zephyr.scraper.query.provider.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.zephyr.scraper.internal.PaginationConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractQueryProviderTest {
    private static final SearchEngine SEARCH_ENGINE = SearchEngine.GOOGLE;
    private static final Keyword KEYWORD = new Keyword();

    private static final String URL = "URL";
    private static final String ROOT = "/";
    private static final String FIRST_PARAM = "FIRST_PARAM";
    private static final String FIRST_VALUE = "FIRST_VALUE";

    private static final String SECOND_PARAM = "SECOND_PARAM";
    private static final String SECOND_VALUE = "SECOND_VALUE";

    private static final String PARAM = "PARAM";
    private static final String VALUE = "VALUE";

    private static final Map<String, ?> FIRST_PAGE_PARAMS = ImmutableMap.of(FIRST_PARAM, FIRST_VALUE);
    private static final Map<String, ?> SECOND_PAGE_PARAMS = ImmutableMap.of(SECOND_PARAM, SECOND_VALUE);
    private static final Map<String, ?> THIRD_PAGE_PARAMS = ImmutableMap.of(PARAM, VALUE);
    private static final Map<String, ?> FOURTH_PAGE_PARAMS = ImmutableMap.of(PARAM, VALUE);
    private static final Map<String, ?> FIFTH_PAGE_PARAMS = ImmutableMap.of(PARAM, VALUE);

    @Mock
    private QueryContext context;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private ScraperProperties.EngineProperties engineProperties;

    @InjectMocks
    private final AbstractQueryProvider testInstance = new AbstractQueryProvider(SEARCH_ENGINE) {

        @Override
        protected String provideBaseUrl(QueryContext context) {
            return URL;
        }

        @Override
        protected Map<String, ?> provideParams(QueryContext context, Page page) {
            switch (page.getPage()) {
                case 0:
                    return FIRST_PAGE_PARAMS;
                case 1:
                    return SECOND_PAGE_PARAMS;
                case 2:
                    return THIRD_PAGE_PARAMS;
                case 3:
                    return FOURTH_PAGE_PARAMS;
                case 4:
                    return FIFTH_PAGE_PARAMS;
                default:
                    throw new IllegalArgumentException("Max page number is 4");
            }
        }
    };

    @Before
    public void setUp() {
        when(scraperProperties.getScraper(SEARCH_ENGINE)).thenReturn(engineProperties);
        when(engineProperties.getFirst()).thenReturn(FIRST_ROW);
        when(engineProperties.getPageSize()).thenReturn(PAGE_SIZE);
        when(engineProperties.getResultCount()).thenReturn(ROW_COUNT);

        when(context.getKeyword()).thenReturn(KEYWORD);
    }

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(context));
    }

    private List<Request> expected() {
        return ImmutableList.of(
                createRequest(FIRST_PAGE_PARAMS),
                createRequest(SECOND_PAGE_PARAMS),
                createRequest(THIRD_PAGE_PARAMS),
                createRequest(FOURTH_PAGE_PARAMS),
                createRequest(FIRST_PAGE_PARAMS)
        );
    }

    private Request createRequest(Map<String, ?> params) {
        return Request.builder()
                .keyword(KEYWORD)
                .provider(SEARCH_ENGINE)
                .baseUrl(URL)
                .uri(ROOT)
                .params(params)
                .build();
    }
}