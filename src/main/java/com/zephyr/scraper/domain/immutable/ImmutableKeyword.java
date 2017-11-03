package com.zephyr.scraper.domain.immutable;

@SuppressWarnings("unused")
public interface ImmutableKeyword {

    String getUserAgent();

    String getLanguageIso();

    String getCountryIso();

    String getPlace();

    String getWord();
}
