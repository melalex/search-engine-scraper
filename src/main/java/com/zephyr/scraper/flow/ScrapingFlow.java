package com.zephyr.scraper.flow;

import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchResult;
import reactor.core.publisher.Flux;

public interface ScrapingFlow {

    Flux<SearchResult> handle(Flux<Keyword> input);
}
