package com.zephyr.scraper.source;

import com.zephyr.scraper.domain.external.PlaceDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface LocationSource {

    Mono<PlaceDto> findPlace(String iso, String name);
}