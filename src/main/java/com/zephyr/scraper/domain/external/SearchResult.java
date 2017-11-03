package com.zephyr.scraper.domain.external;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SearchResult {
    private int offset;
    private Keyword keyword;
    private LocalDateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}