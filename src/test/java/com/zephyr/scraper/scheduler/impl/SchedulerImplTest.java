package com.zephyr.scraper.scheduler.impl;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.scheduler.strategy.RequestStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerImplTest {
    private EngineRequest engineRequest;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private RequestStrategy directRequestStrategy;

    @Mock
    private RequestStrategy proxyRequestStrategy;

    @InjectMocks
    private SchedulerImpl testInstance;

    @Before
    public void setUp() {
        when(directRequestStrategy.configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any()))
                .thenReturn(context());
        when(proxyRequestStrategy.configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any()))
                .thenReturn(context());

        engineRequest = DomainUtils.requestWith(DomainUtils.ANY_PROVIDER);
    }

    @Test
    public void shouldCreateContextDirect() {
        when(scraperProperties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(direct());

        test();

        verify(directRequestStrategy).configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any());
    }

    @Test
    public void shouldCreateContextProxy() {
        when(scraperProperties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(proxy());

        test();

        verify(proxyRequestStrategy).configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any());
    }

    @Test
    public void shouldReportDirect() {
        when(scraperProperties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(direct());

        test();

        verify(directRequestStrategy).configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any());
    }

    @Test
    public void shouldReportProxy() {
        when(scraperProperties.getScraper(DomainUtils.ANY_PROVIDER)).thenReturn(proxy());

        test();

        verify(proxyRequestStrategy).configureAndBuild(eq(DomainUtils.ANY_PROVIDER), any());
    }

    private void test() {
        StepVerifier.create(testInstance.createContext(engineRequest))
                .expectNext(DomainUtils.ANY_REQUEST_CONTEXT)
                .verifyComplete();
    }

    private Mono<RequestContext> context() {
        return Mono.just(DomainUtils.ANY_REQUEST_CONTEXT);
    }

    private ScraperProperties.EngineProperties direct() {
        ScraperProperties.EngineProperties engineProperties = new ScraperProperties.EngineProperties();
        engineProperties.setUseProxy(false);

        return engineProperties;
    }

    private ScraperProperties.EngineProperties proxy() {
        ScraperProperties.EngineProperties engineProperties = new ScraperProperties.EngineProperties();
        engineProperties.setUseProxy(true);

        return engineProperties;
    }
}