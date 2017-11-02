package com.zephyr.scraper.loader.context.model;

import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.domain.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.util.Map;

@Value
@Builder
public class RequestContext {
    private String taskId;
    private String languageIso;
    private String userAgent;

    private int iteration;
    private SearchEngine provider;
    private String baseUrl;
    private String uri;
    private Map<String, ?> params;
    private int number;

    private Proxy proxy;
    private Duration duration;

    public String getFullUrl() {
        return baseUrl + uri;
    }
}