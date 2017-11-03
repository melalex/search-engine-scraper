package com.zephyr.scraper.domain;

import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Delegate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
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

    public interface ImmutableKeyword {

        String getUserAgent();

        String getLanguageIso();

        String getCountryIso();

        String getPlace();

        String getWord();
    }
}