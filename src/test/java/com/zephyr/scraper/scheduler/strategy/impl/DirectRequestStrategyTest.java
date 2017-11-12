package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.internal.TimeUtils;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Mock
    private ScraperProperties properties;

    @Mock
    private Clock clock;

    private DirectRequestStrategy testInstance;

    @Before
    public void setUp() {
        TimeUtils.configureClock(clock);

        testInstance = new DirectRequestStrategy();
        testInstance.setClock(clock);
        testInstance.setProperties(properties);

        when(properties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(createProperties());
    }

    @Test
    public void shouldConfigure() {
        StepVerifier.create(testInstance.configureAndBuild(DomainUtils.ANY_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ZERO))
                .verifyComplete();

        StepVerifier.create(testInstance.configureAndBuild(DomainUtils.ANY_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ofMillis(DELAY)))
                .verifyComplete();
    }

    @Test
    public void shouldReport() {
        StepVerifier.create(testInstance.configureAndBuild(DomainUtils.ANY_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ZERO))
                .verifyComplete();

        testInstance.report(DomainUtils.requestContextWithEngine(DomainUtils.ANY_PROVIDER));

        StepVerifier.create(testInstance.configureAndBuild(DomainUtils.ANY_PROVIDER, RequestContext.builder()))
                .expectNext(expected(Duration.ofMillis(DELAY + ERROR_DELAY)))
                .verifyComplete();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotReport() {
        testInstance.report(DomainUtils.requestContextWithEngine(DomainUtils.ANY_PROVIDER));
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