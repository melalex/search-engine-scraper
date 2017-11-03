package com.zephyr.scraper.query.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class QueryConstructorImpl implements QueryConstructor {

    @Setter(onMethod = @__(@Autowired))
    private List<QueryProvider> providers;

    @Override
    public Flux<Request> construct(Keyword keyword) {
        return Flux.fromIterable(providers).map(p -> p.provide(keyword));
    }
}
