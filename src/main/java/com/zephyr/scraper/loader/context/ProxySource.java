package com.zephyr.scraper.loader.context;

import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.domain.SearchEngine;
import reactor.core.publisher.Mono;

public interface ProxySource {

    Mono<Proxy> reserve(SearchEngine engine);

    Mono<Void> report(String id, SearchEngine engine);
}