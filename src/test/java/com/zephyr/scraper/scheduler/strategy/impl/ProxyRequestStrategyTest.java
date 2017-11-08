package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.Proxy;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.internal.TimeUtils;
import com.zephyr.scraper.source.ProxySource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Duration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProxyRequestStrategyTest {
    private static final long DELAY = 5000;
    private static final String PROXY_ID = "PROXY_ID";
    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;
    private static final Duration DURATION = Duration.ofMillis(DELAY);

    @Mock
    private Proxy proxy;

    @Mock
    private ProxySource proxySource;

    @Mock
    private Clock clock;

    @InjectMocks
    private ProxyRequestStrategy testInstance;

    @Before
    public void setUp() {
        TimeUtils.configureClock(clock);

        when(proxy.getId()).thenReturn(PROXY_ID);
        when(proxy.getSchedule()).thenReturn(TimeUtils.now().plus(DURATION));
        when(proxySource.reserve(PROVIDER)).thenReturn(Mono.just(proxy));
        when(proxySource.report(PROXY_ID, PROVIDER)).thenReturn(Mono.empty());
    }

    @Test
    public void shouldConfigure() {
        StepVerifier.create(testInstance.configureAndBuild(PROVIDER, RequestContext.builder()))
                .expectNext(expected())
                .verifyComplete();
    }

    @Test
    public void shouldReport() {
        testInstance.report(expected());

        verify(proxySource).report(PROXY_ID, PROVIDER);
    }

    private RequestContext expected() {
        return RequestContext.builder()
                .duration(DURATION)
                .proxy(proxy)
                .build();
    }
}