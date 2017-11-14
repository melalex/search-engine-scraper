package com.zephyr.scraper.query.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.internal.DomainUtils;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryConstructorImplTest {
    private static final String COUNTRY_ISO = "COUNTRY_ISO";
    private static final String PLACE_NAME = "PLACE_NAME";

    private static final String FIRST_URL = "FIRST_URL";
    private static final String SECOND_URL = "SECOND_URL";

    private Keyword keyword;
    private EngineRequest firstEngineRequest;
    private EngineRequest secondEngineRequest;

    @Mock
    private QueryProvider firstProvider;

    @Mock
    private QueryProvider secondProvider;

    @Mock
    private LocationSource locationSource;

    @InjectMocks
    private QueryConstructorImpl testInstance;

    @Before
    public void setUp() {
        testInstance.setProviders(ImmutableList.of(firstProvider, secondProvider));

        keyword = createKeyword();

        firstEngineRequest = EngineRequest.builder()
                .keyword(keyword)
                .baseUrl(FIRST_URL)
                .build();

        secondEngineRequest = EngineRequest.builder()
                .keyword(keyword)
                .baseUrl(SECOND_URL)
                .build();

        when(locationSource.findPlace(COUNTRY_ISO, PLACE_NAME))
                .thenReturn(Mono.just(DomainUtils.ANY_PLACE));
        when(firstProvider.provide(QueryContext.of(keyword, DomainUtils.ANY_PLACE)))
                .thenReturn(ImmutableList.of(firstEngineRequest));
        when(secondProvider.provide(QueryContext.of(keyword, DomainUtils.ANY_PLACE)))
                .thenReturn(ImmutableList.of(secondEngineRequest));
    }

    @Test
    public void shouldConstruct() {
        StepVerifier.create(testInstance.construct(keyword))
                .expectNext(firstEngineRequest, secondEngineRequest)
                .verifyComplete();
    }

    private Keyword createKeyword() {
        Keyword keyword = new Keyword();
        keyword.setPlace(PLACE_NAME);
        keyword.setCountryIso(COUNTRY_ISO);

        return keyword;
    }
}