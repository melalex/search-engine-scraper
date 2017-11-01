package com.zephyr.scraper.domain;

import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class PageRequest {
    private Map<String, ?> params;
    private int number;
}
