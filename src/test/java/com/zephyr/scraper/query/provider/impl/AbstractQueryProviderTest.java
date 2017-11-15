package com.zephyr.scraper.query.provider.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Page;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.internal.PaginationConstants;
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
    private static final Keyword KEYWORD = new Keyword();

    private static final String URL = "URL";
    private static final String ROOT = "/";
    private static final String FIRST_PARAM = "FIRST_PARAM";
    private static final String FIRST_VALUE = "FIRST_VALUE";

    private static final String SECOND_PARAM = "SECOND_PARAM";
    private static final String SECOND_VALUE = "SECOND_VALUE";

    private static final String PARAM = "PARAM";
    private static final String VALUE = "VALUE";

    private static final Map<String, List<String>> FIRST_PAGE_PARAMS = ImmutableMap.of(FIRST_PARAM, ImmutableList.of(FIRST_VALUE));
    private static final Map<String, List<String>> SECOND_PAGE_PARAMS = ImmutableMap.of(SECOND_PARAM, ImmutableList.of(SECOND_VALUE));
    private static final Map<String, List<String>> THIRD_PAGE_PARAMS = ImmutableMap.of(PARAM, ImmutableList.of(VALUE));
    private static final Map<String, List<String>> FOURTH_PAGE_PARAMS = ImmutableMap.of(PARAM, ImmutableList.of(VALUE));
    private static final Map<String, List<String>> FIFTH_PAGE_PARAMS = ImmutableMap.of(PARAM, ImmutableList.of(VALUE));

    private QueryContext context;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private ScraperProperties.EngineProperties engineProperties;

    @InjectMocks
    private final AbstractQueryProvider testInstance = new AbstractQueryProvider(DomainUtils.ANY_PROVIDER) {

        @Override
        protected String provideBaseUrl(QueryContext context) {
            return URL;
        }

        @Override
        protected Map<String, List<String>> provideParams(QueryContext context, Page page) {
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
        when(scraperProperties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(engineProperties);
        when(engineProperties.getFirst()).thenReturn(FIRST_ROW);
        when(engineProperties.getPageSize()).thenReturn(PAGE_SIZE);
        when(engineProperties.getResultCount()).thenReturn(ROW_COUNT);

        context = QueryContext.of(DomainUtils.ANY_KEYWORD, DomainUtils.ANY_PLACE);
    }

    @Test
    public void shouldProvide() {
        assertEquals(expected(), testInstance.provide(context));
    }

    private List<EngineRequest> expected() {
        return ImmutableList.of(
                createRequest(FIRST_PAGE_PARAMS, PaginationConstants.FIRST_PAGE.getStart()),
                createRequest(SECOND_PAGE_PARAMS, PaginationConstants.SECOND_PAGE.getStart()),
                createRequest(THIRD_PAGE_PARAMS, PaginationConstants.THIRD_PAGE.getStart()),
                createRequest(FOURTH_PAGE_PARAMS, PaginationConstants.FORTH_PAGE.getStart()),
                createRequest(FIFTH_PAGE_PARAMS, PaginationConstants.LAST_PAGE.getStart())
        );
    }

    private EngineRequest createRequest(Map<String, List<String>> params, int offset) {
        return EngineRequest.builder()
                .keyword(KEYWORD)
                .provider(DomainUtils.ANY_PROVIDER)
                .baseUrl(URL)
                .uri(ROOT)
                .params(params)
                .offset(offset)
                .build();
    }
}