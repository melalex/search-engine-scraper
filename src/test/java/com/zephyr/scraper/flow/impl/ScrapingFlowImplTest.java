package com.zephyr.scraper.flow.impl;

import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.internal.TimeUtils;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.QueryConstructor;
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

import java.time.*;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScrapingFlowImplTest {
    private static final int BACKOFF = 5000;
    private static final int RETRY_COUNT = 3;
    private static final int OFFSET = 0;
    private static final int SCHEDULER_CALLS_COUNT = 2;

    private static final long DURATION_MINUTES = 7;

    private static final SearchEngine PROVIDER = SearchEngine.GOOGLE;
    private static final Duration DURATION = Duration.ofMinutes(DURATION_MINUTES);
    private static final Duration BACKOFF_DURATION = Duration.ofMillis(BACKOFF);

    private final RequestContext context = createRequestContext();

    private final List<String> links = createLinks();

    @Mock
    private Keyword keyword;

    @Mock
    private Request request;

    @Mock
    private Response response;

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

    @InjectMocks
    private ScrapingFlowImpl testInstance;

    @Before
    public void setUp() {
        TimeUtils.configureClock(clock);

        when(request.getProvider()).thenReturn(PROVIDER);
        when(request.getOffset()).thenReturn(OFFSET);
        when(request.getKeyword()).thenReturn(keyword);
        when(queryConstructor.construct(keyword)).thenReturn(Flux.just(request));

        when(scheduler.createContext(request)).thenReturn(Mono.just(context));
        when(scraperProperties.getBrowser()).thenReturn(createBrowserProperties());

        when(browser.get(context)).thenReturn(response());

        when(crawler.crawl(PROVIDER, response)).thenReturn(links);
    }

    @Test
    public void shouldHandle() {
        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(keyword)))
                .expectNoEvent(DURATION)
                .expectNext(expected())
                .verifyComplete();
    }

    @Test
    public void shouldRetry() {
        when(browser.get(context))
                .thenReturn(failedResponse())
                .thenReturn(response());

        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(keyword)))
                .expectNoEvent(DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNext(expected())
                .verifyComplete();
    }

    @Test
    public void shouldRetryWithNewContext() {
        when(browser.get(context))
                .thenReturn(failedResponse())
                .thenReturn(failedResponse())
                .thenReturn(failedResponse())
                .thenReturn(response());

        StepVerifier.withVirtualTime(() -> testInstance.handle(Flux.just(keyword)))
                .expectNoEvent(DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(BACKOFF_DURATION)
                .expectNoEvent(DURATION)
                .expectNext(expected())
                .verifyComplete();

        verify(scheduler, times(SCHEDULER_CALLS_COUNT)).createContext(request);
        verify(scheduler).report(context);
    }

    private SearchResult expected() {
        return SearchResult.builder()
                .offset(OFFSET)
                .keyword(keyword)
                .links(links)
                .provider(PROVIDER)
                .timestamp(TimeUtils.now())
                .build();
    }

    private RequestContext createRequestContext() {
        return RequestContext.builder()
                .duration(DURATION)
                .request(request)
                .build();
    }

    private Mono<Response> response() {
        return Mono.just(response);
    }

    private Mono<Response> failedResponse() {
        return Mono.error(new BrowserException("BrowserException"));
    }

    private List<String> createLinks() {
        return List.of("Link1", "Link1", "Link1");
    }

    private ScraperProperties.BrowserProperties createBrowserProperties() {
        ScraperProperties.BrowserProperties browserProperties = new ScraperProperties.BrowserProperties();

        browserProperties.setBackoff(BACKOFF);
        browserProperties.setRetryCount(RETRY_COUNT);

        return browserProperties;
    }
}