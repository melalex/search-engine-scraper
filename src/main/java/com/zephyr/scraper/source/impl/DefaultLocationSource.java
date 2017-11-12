package com.zephyr.scraper.source.impl;

import com.zephyr.scraper.domain.external.CountryDto;
import com.zephyr.scraper.domain.external.PlaceDto;
import com.zephyr.scraper.domain.external.PlaceType;
import com.zephyr.scraper.source.LocationSource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class DefaultLocationSource implements LocationSource {
    private static final String DEFAULT_COUNTRY_ISO = "UA";
    private static final String DEFAULT_LOCAL_GOOGLE = "https://www.google.com.ua";
    private static final String DEFAULT_LOCAL_YANDEX = "https://yandex.ua";
    private static final String DEFAULT_COUNTRY_NAME = "Ukraine";

    private static final long DEFAULT_PLACE_ID = 1012852;
    private static final String DEFAULT_PLACE_NAME = "Kiev";
    private static final String DEFAULT_CANONICAL_NAME = "Kiev,Kyiv city,Ukraine";
    private static final String DEFAULT_LOCATION = "w+CAIQICIWS2lldixLeWl2IGNpdHksVWtyYWluZQ";
    private static final int DEFAULT_PARENT = 21118;
    private static final PlaceType DEFAULT_PLACE_TYPE = PlaceType.CITY;

    private PlaceDto defaultPlace;

    @PostConstruct
    public void init() {
        CountryDto defaultCountry = new CountryDto();
        defaultCountry.setIso(DEFAULT_COUNTRY_ISO);
        defaultCountry.setLocaleGoogle(DEFAULT_LOCAL_GOOGLE);
        defaultCountry.setLocaleYandex(DEFAULT_LOCAL_YANDEX);
        defaultCountry.setName(DEFAULT_COUNTRY_NAME);

        defaultPlace = new PlaceDto();
        defaultPlace.setId(DEFAULT_PLACE_ID);
        defaultPlace.setName(DEFAULT_PLACE_NAME);
        defaultPlace.setCanonicalName(DEFAULT_CANONICAL_NAME);
        defaultPlace.setCountry(defaultCountry);
        defaultPlace.setLocation(DEFAULT_LOCATION);
        defaultPlace.setParent(DEFAULT_PARENT);
        defaultPlace.setType(DEFAULT_PLACE_TYPE);
    }

    @Override
    public Mono<PlaceDto> findPlace(String iso, String name) {
        return Mono.just(defaultPlace);
    }
}