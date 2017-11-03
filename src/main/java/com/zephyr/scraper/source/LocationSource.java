package com.zephyr.scraper.source;

import com.zephyr.scraper.domain.external.CountryDto;
import com.zephyr.scraper.domain.external.PlaceDto;
import reactor.core.publisher.Mono;

public interface LocationSource {

    Mono<CountryDto> findCountry(String iso);

    Mono<PlaceDto> findPlace(String iso, String name);
}