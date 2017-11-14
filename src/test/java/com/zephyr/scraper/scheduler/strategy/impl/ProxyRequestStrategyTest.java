package com.zephyr.scraper.scheduler.strategy.impl;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.Proxy;
import com.zephyr.scraper.internal.DomainUtils;
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
    private static final Duration DURATION = Duration.ofMillis(DELAY);

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

        proxy = createProxy();

        when(proxySource.reserve(DomainUtils.ANY_PROVIDER)).thenReturn(Mono.just(proxy));
        when(proxySource.report(PROXY_ID, DomainUtils.ANY_PROVIDER)).thenReturn(Mono.empty());
    }

    private Proxy createProxy() {
        Proxy proxy = new Proxy();
        proxy.setId(PROXY_ID);
        proxy.setSchedule(TimeUtils.now().plus(DURATION));

        return proxy;
    }

    @Test
    public void shouldConfigure() {
        RequestContext.RequestContextBuilder builder = RequestContext.builder()
                .engineRequest(EngineRequest.builder().provider(DomainUtils.ANY_PROVIDER).build());

        StepVerifier.create(testInstance.configureAndBuild(DomainUtils.ANY_PROVIDER, builder))
                .expectNext(expected())
                .verifyComplete();
    }

    @Test
    public void shouldReport() {
        testInstance.report(expected());

        verify(proxySource).report(PROXY_ID, DomainUtils.ANY_PROVIDER);
    }

    private RequestContext expected() {
        return RequestContext.builder()
                .duration(DURATION)
                .proxy(proxy)
                .engineRequest(EngineRequest.builder().provider(DomainUtils.ANY_PROVIDER).build())
                .build();
    }
}