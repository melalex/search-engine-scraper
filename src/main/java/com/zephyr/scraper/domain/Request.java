package com.zephyr.scraper.domain;

import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.immutable.ImmutableKeyword;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Delegate;

import java.util.Map;

@Value
@Builder
public class Request {

    @Delegate(types = ImmutableKeyword.class)
    private Keyword keyword;

    private SearchEngine provider;
    private String baseUrl;
    private String uri;

    private int offset;
    private Map<String, ?> params;
}