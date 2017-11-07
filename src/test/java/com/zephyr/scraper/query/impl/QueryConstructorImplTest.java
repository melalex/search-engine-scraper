package com.zephyr.scraper.query.impl;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.PlaceDto;
import com.zephyr.scraper.query.provider.QueryProvider;
import com.zephyr.scraper.source.LocationSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryConstructorImplTest {
    private static final String COUNTRY_ISO = "COUNTRY_ISO";
    private static final String PLACE_NAME = "PLACE_NAME";

    private static final String FIRST_URL = "FIRST_URL";
    private static final String SECOND_URL = "SECOND_URL";

    @Mock
    private Keyword keyword;

    @Mock
    private PlaceDto place;

    @Mock
    private QueryProvider firstProvider;

    @Mock
    private QueryProvider secondProvider;

    @Mock
    private LocationSource locationSource;

    @InjectMocks
    private QueryConstructorImpl testInstance;

    private Request firstRequest = Request.builder()
            .keyword(keyword)
            .baseUrl(FIRST_URL)
            .build();

    private Request secondRequest = Request.builder()
            .keyword(keyword)
            .baseUrl(SECOND_URL)
            .build();

    @Before
    public void setUp() {
        testInstance.setProviders(List.of(firstProvider, secondProvider));

        when(locationSource.findPlace(COUNTRY_ISO, PLACE_NAME)).thenReturn(Mono.just(place));
        when(firstProvider.provide(QueryContext.of(keyword, place))).thenReturn(List.of(firstRequest));
        when(secondProvider.provide(QueryContext.of(keyword, place))).thenReturn(List.of(secondRequest));
    }

    @Test
    public void shouldConstruct() {
        StepVerifier.create(testInstance.construct(keyword))
                .expectNext(firstRequest, secondRequest);
    }
}