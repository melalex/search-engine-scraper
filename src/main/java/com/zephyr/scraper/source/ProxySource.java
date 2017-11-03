package com.zephyr.scraper.source;

import com.zephyr.scraper.domain.external.Proxy;
import com.zephyr.scraper.domain.external.SearchEngine;
import reactor.core.publisher.Mono;

public interface ProxySource {

    Mono<Proxy> reserve(SearchEngine engine);

    Mono<Void> report(String id, SearchEngine engine);
}