package com.zephyr.scraper.domain.immutable;

import com.zephyr.scraper.domain.external.CountryDto;
import com.zephyr.scraper.domain.external.PlaceType;

@SuppressWarnings("unused")
public interface ImmutablePlace {

    String getLocation();

    PlaceType getType();

    CountryDto getCountry();

    long getParent();

    String getCanonicalName();

    String getName();

    long getId();
}
