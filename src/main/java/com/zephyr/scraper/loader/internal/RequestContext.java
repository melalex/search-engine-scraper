package com.zephyr.scraper.loader.internal;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.domain.SearchEngine;
import lombok.Data;

@Data
public class RequestContext {
    private ScraperTask task;
    private SearchEngine provider;
    private Proxy proxy;
    private String baseUrl;
    private String uri;
    private PageRequest page;

    public String getFullUrl() {
        return baseUrl + uri;
    }
}