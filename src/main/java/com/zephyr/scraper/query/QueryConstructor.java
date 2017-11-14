package com.zephyr.scraper.query;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.external.Keyword;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface QueryConstructor {

    Flux<EngineRequest> construct(Keyword keyword);
}
