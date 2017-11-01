package com.zephyr.scraper.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SearchResult {
    private ScraperTask task;
    private LocalDateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}