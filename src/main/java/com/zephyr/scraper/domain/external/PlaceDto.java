package com.zephyr.scraper.domain.external;

import lombok.Data;

@Data
public class PlaceDto {
    private long id;
    private String name;
    private String canonicalName;
    private long parent;
    private CountryDto country;
    private PlaceType type;
    private String location;
}
