package com.zephyr.scraper.domain;

import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class Request {
    private Keyword keyword;

    private SearchEngine provider;
    private String baseUrl;
    private String uri;

    private int offset;
    private Map<String, ?> params;

    public String getUserAgent() {
        return keyword.getUserAgent();
    }

    public String getLanguageIso() {
        return keyword.getLanguageIso();
    }
}