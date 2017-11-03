package com.zephyr.scraper.domain.external;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlaceType {
    AIRPORT("Airport"),
    AUTONOMOUS_COMMUNITY("Autonomous Community"),
    BOROUGH("Borough"),
    CANTON("Canton"),
    CITY("City"),
    CITY_REGION("City Region"),
    CONGRESSIONAL_DISTRICT("Congressional District"),
    COUNTRY("Country"),
    COUNTY("County"),
    DEPARTMENT("Department"),
    DISTRICT("District"),
    GOVERNORATE("Governorate"),
    MUNICIPALITY("Municipality"),
    NATIONAL_PARK("National Park"),
    NEIGHBORHOOD("Neighborhood"),
    OKRUG("Okrug"),
    POSTAL_CODE("Postal Code"),
    PREFECTURE("Prefecture"),
    PROVINCE("Province"),
    REGION("Region"),
    STATE("State"),
    TERRITORY("Territory"),
    TV_REGION("TV Region"),
    UNION_TERRITORY("Union Territory"),
    UNIVERSITY("University");

    private final String value;

    PlaceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PlaceType of(final String name) {
        for (final PlaceType value : values()) {
            if (value.getValue().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("There is no PlaceType with name '" + name + "'");
    }
}
