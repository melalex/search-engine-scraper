package com.zephyr.scraper.scheduler.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.properties.ScraperProperties;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerImplTest {
    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;

    @Mock
    private Request request;

    @Mock
    private RequestContext context;

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
        when(directRequestStrategy.configureAndBuild(PROVIDER, any())).thenReturn(context());
        when(proxyRequestStrategy.configureAndBuild(PROVIDER, any())).thenReturn(context());

        when(request.getProvider()).thenReturn(PROVIDER);
    }

    @Test
    public void shouldCreateContextDirect() {
        when(scraperProperties.getScraper(PROVIDER)).thenReturn(direct());

        test();

        verify(directRequestStrategy).configureAndBuild(PROVIDER, any());
    }

    @Test
    public void shouldCreateContextProxy() {
        when(scraperProperties.getScraper(PROVIDER)).thenReturn(proxy());

        test();

        verify(proxyRequestStrategy).configureAndBuild(PROVIDER, any());
    }

    @Test
    public void shouldReportDirect() {
        when(scraperProperties.getScraper(PROVIDER)).thenReturn(direct());

        test();

        verify(directRequestStrategy).configureAndBuild(PROVIDER, any());
    }

    @Test
    public void shouldReportProxy() {
        when(scraperProperties.getScraper(PROVIDER)).thenReturn(proxy());

        test();

        verify(proxyRequestStrategy).configureAndBuild(PROVIDER, any());
    }

    private void test() {
        StepVerifier.create(testInstance.createContext(request))
                .expectNext(context)
                .verifyComplete();
    }

    private Mono<RequestContext> context() {
        return Mono.just(context);
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