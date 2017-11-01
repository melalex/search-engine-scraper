package com.zephyr.scraper.flow;

import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchResult;
import reactor.core.publisher.Flux;

public interface ScrapingFlow {

    Flux<SearchResult> handle(Flux<ScraperTask> input);
}
