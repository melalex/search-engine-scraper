package com.zephyr.scraper.flow.impl;

import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchResult;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.query.QueryConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@Slf4j
public class ScrapingFlowImpl implements ScrapingFlow {

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor queryConstructor;

    @Setter(onMethod = @__(@Autowired))
    private PageLoader pageLoader;

    @Setter(onMethod = @__(@Autowired))
    private DocumentCrawler documentCrawler;

    @Override
    public Flux<SearchResult> handle(Flux<ScraperTask> input) {
        return input
                .doOnNext(t -> log.info("Received Task: {}", t))
                .flatMap(t -> queryConstructor.construct(t))
                .doOnNext(r -> log.info("Request constructed: {}", r))
                .parallel()
                .flatMap(r -> pageLoader.load(r))
                .doOnNext(r -> log.info("Finished loading pages for TaskDto {} and Engine {}", r.getTask().getId(), r.getProvider()))
                .map(d -> documentCrawler.crawl(d))
                .doOnNext(r -> log.info("Finished crawling: {}", r))
                .sequential()
                .doOnError(t -> log.error("Unhandled exception", t));
    }
}