package com.zephyr.scraper.domain.external;

import lombok.Data;

@Data
public class Keyword {
    private String word;
    private String place;
    private String countryIso;
    private String languageIso;
    private String userAgent;
}