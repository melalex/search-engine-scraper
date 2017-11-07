package com.zephyr.scraper.flow.impl;

import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.context.ContextManager;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.QueryConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScrapingFlowImplTest {

    @Mock
    private Keyword keyword;

    @Mock
    private Request request;

    @Mock
    private ScraperProperties scraperProperties;

    @Mock
    private ContextManager contextManager;

    @Mock
    private QueryConstructor queryConstructor;

    @Mock
    private Browser browser;

    @Mock
    private Crawler crawler;

    @InjectMocks
    private ScrapingFlowImpl testInstance;

    @Before
    public void setUp() {
        when(queryConstructor.construct(keyword)).thenReturn(Flux.just(request));

        when(contextManager.toContext(request)).thenReturn(Mono.just(createRequestContext()));
    }

    @Test
    public void shouldHandle() {
        StepVerifier.create(testInstance.handle(Flux.just(keyword)))
                .expectNext(expected());
    }

    private SearchResult expected() {
        return SearchResult.builder()
                .build();
    }

    private RequestContext createRequestContext() {
        return RequestContext.builder()
                .build();
    }
}