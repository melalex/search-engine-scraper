package com.zephyr.scraper.source.impl;

import com.zephyr.scraper.domain.external.Proxy;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.source.ProxySource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultProxySource implements ProxySource {

    @Override
    public Mono<Proxy> reserve(SearchEngine engine) {
        throw new UnsupportedOperationException("No implementation");
    }

    @Override
    public Mono<Void> report(String id, SearchEngine engine) {
        throw new UnsupportedOperationException("No implementation");
    }
}
