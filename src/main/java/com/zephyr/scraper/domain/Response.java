package com.zephyr.scraper.domain;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value(staticConstructor = "of")
public class Response {
    private Map<String, List<String>> headers;
    private String body;
    private int page;
}