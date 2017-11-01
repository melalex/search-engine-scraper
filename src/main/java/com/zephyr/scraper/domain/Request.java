package com.zephyr.scraper.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Request {
    private ScraperTask task;
    private SearchEngine provider;
    private String baseUrl;
    private String uri;
    private List<PageRequest> pages;
}