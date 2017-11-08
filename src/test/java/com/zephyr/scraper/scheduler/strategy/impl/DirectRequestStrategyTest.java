package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.TimeUtils;
import com.zephyr.scraper.properties.ScraperProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Duration;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectRequestStrategyTest {
    private static final long DELAY = 5000;
    private static final long ERROR_DELAY = 5000;

    private static final SearchEngine FIRST_PROVIDER = SearchEngine.GOOGLE;
    private static final SearchEngine SECOND_PROVIDER = SearchEngine.BING;

    @Mock
    private ScraperProperties properties;

    @Mock
    private Clock clock;

    @InjectMocks
    private DirectRequestStrategy testInstance;

    @Before
    public void setUp() {
        TimeUtils.configureClock(clock);

        when(properties.getScraper(FIRST_PROVIDER)).thenReturn(createProperties());
    }

    @Test
    public void shouldConfigure() {
        StepVerifier.create(testInstance.configureAndBuild(FIRST_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ZERO))
                .verifyComplete();

        StepVerifier.create(testInstance.configureAndBuild(FIRST_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ofMinutes(DELAY)))
                .verifyComplete();
    }

    @Test
    public void shouldReport() {
        StepVerifier.create(testInstance.configureAndBuild(FIRST_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ofMinutes(DELAY + ERROR_DELAY)))
                .verifyComplete();
    }

    @Test
    public void shouldNotReport() {
        StepVerifier.create(testInstance.configureAndBuild(SECOND_PROVIDER, RequestContext.builder()))
                .verifyError(IllegalArgumentException.class);
    }

    private RequestContext expected(Duration duration) {
        return RequestContext.builder()
                .duration(duration)
                .build();
    }

    private ScraperProperties.EngineProperties createProperties() {
        ScraperProperties.EngineProperties engineProperties = new ScraperProperties.EngineProperties();
        engineProperties.setDelay(DELAY);
        engineProperties.setErrorDelay(ERROR_DELAY);

        return engineProperties;
    }
}