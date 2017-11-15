package com.zephyr.scraper.flow.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.internal.DomainUtils;
import com.zephyr.scraper.internal.TimeUtils;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.saver.ResponseSaver;
import com.zephyr.scraper.scheduler.Scheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScrapingFlowImplTest {
    private static final int BACKOFF = 5000;
    private static final int RETRY_COUNT = 3;
    private static final int OFFSET = 0;
    private static final int SCHEDULER_CALLS_COUNT = 2;

    private static final long DURATION_MINUTES = 7;

    private static final Duration DURATION = Duration.ofMinutes(DURATION_MINUTES);
    private static final Duration BACKOFF_DURATION = Duration.ofMillis(BACKOFF);

    private RequestContext context;
    private List<String> links;
    private EngineRequest engineRequest;
    private EngineResponse engineResponse;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private Scheduler scheduler;

    @Mock
    private QueryConstructor queryConstructor;

    @Mock
    private Browser browser;

    @Mock
    private Crawler crawler;

    @Mock
    private Clock clock;

    @Mock
    private ResponseSaver responseSaver;

    @InjectMocks
    private ScrapingFlowImpl testInstance;

    @Before
    public void setUp() {
        TimeUtils.configureClock(clock);

        engineRequest = DomainUtils.requestWith(DomainUtils.ANY_KEYWORD, DomainUtils.ANY_PROVIDER, OFFSET);
        engineResponse = DomainUtils.responseWith(DomainUtils.ANY_PROVIDER);
        context = createRequestContext();
        links = createLinks();

        when(queryConstructor.construct(DomainUtils.ANY_KEYWORD)).thenReturn(Flux.just(engineRequest));

        when(scheduler.createContext(engineRequest)).thenReturn(Mono.just(context));
        when(scraperProperties.getBrowser()).thenReturn(createBrowserProperties());

        when(browser.get(context)).thenReturn(response());

        when(crawler.crawl(DomainUtils.ANY_PROVIDER, engineResponse)).thenReturn(links);
    }

    @Test
    public void shouldHandle() {
        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(DomainUtils.ANY_KEYWORD)))
                .expectSubscription()
                .expectNoEvent(DURATION)
                .expectNext(expected())
                .verifyComplete();

        verify(responseSaver).save(engineResponse);
    }

    @Test
    public void shouldRetry() {
        when(browser.get(context))
                .thenReturn(failedResponse())
                .thenReturn(response());

        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(DomainUtils.ANY_KEYWORD)))
                .expectSubscription()
                .expectNoEvent(DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNext(expected())
                .verifyComplete();

        verify(responseSaver).save(engineResponse);
    }

    @Test
    public void shouldRetryWithNewContext() {
        when(browser.get(context))
                .thenReturn(failedResponse())
                .thenReturn(failedResponse())
                .thenReturn(failedResponse())
                .thenReturn(failedResponse())
                .thenReturn(response());

        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(DomainUtils.ANY_KEYWORD)))
                .expectSubscription()
                .expectNoEvent(DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(DURATION)
                .expectNext(expected())
                .verifyComplete();

        verify(scheduler, times(SCHEDULER_CALLS_COUNT)).createContext(engineRequest);
        verify(scheduler).report(context);
        verify(responseSaver).save(engineResponse);
    }

    private SearchResult expected() {
        return SearchResult.builder()
                .offset(OFFSET)
                .keyword(DomainUtils.ANY_KEYWORD)
                .links(links)
                .provider(DomainUtils.ANY_PROVIDER)
                .timestamp(TimeUtils.now())
                .build();
    }

    private RequestContext createRequestContext() {
        return RequestContext.builder()
                .duration(DURATION)
                .engineRequest(engineRequest)
                .build();
    }

    private Mono<EngineResponse> response() {
        return Mono.just(engineResponse);
    }

    private Mono<EngineResponse> failedResponse() {
        return Mono.error(new BrowserException("BrowserException"));
    }

    private List<String> createLinks() {
        return ImmutableList.of("Link1", "Link1", "Link1");
    }

    private ScraperProperties.BrowserProperties createBrowserProperties() {
        ScraperProperties.BrowserProperties browserProperties = new ScraperProperties.BrowserProperties();

        browserProperties.setBackoff(BACKOFF);
        browserProperties.setRetryCount(RETRY_COUNT);

        return browserProperties;
    }
}