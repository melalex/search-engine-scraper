package com.zephyr.scraper.query.impl;

import com.zephyr.scraper.domain.QueryContext;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.query.provider.QueryProvider;
import com.zephyr.scraper.source.LocationSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class QueryConstructorImpl implements QueryConstructor {

    @Setter(onMethod = @__(@Autowired))
    private List<QueryProvider> providers;

    @Setter(onMethod = @__(@Autowired))
    private LocationSource locationSource;

    @Override
    public Flux<Request> construct(Keyword keyword) {
        return Flux.fromIterable(providers)
                .flatMap(p -> toQueryContext(keyword).map(p::provide))
                .flatMap(Flux::fromIterable)
                .doOnNext(r -> log.info("Construct Request: {}", r));
    }

    private Mono<QueryContext> toQueryContext(Keyword keyword) {
        return locationSource.findPlace(keyword.getCountryIso(), keyword.getPlace())
                .doOnNext(p -> log.info("Received Place: {}", p))
                .map(p -> QueryContext.of(keyword, p));
    }
}