package com.zephyr.scraper.domain;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Response {
    private ScraperTask task;
    private SearchEngine provider;
    private List<PageResponse> documents;
}