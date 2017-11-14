package com.zephyr.scraper.domain;

import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value(staticConstructor = "of")
public class EngineResponse {
    private Map<String, List<String>> headers;
    private String body;
    private SearchEngine provider;
}