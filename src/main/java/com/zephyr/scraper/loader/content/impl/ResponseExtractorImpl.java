package com.zephyr.scraper.loader.content.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.content.ResponseExtractor;
import com.zephyr.scraper.loader.content.provider.ContentProvider;
import com.zephyr.scraper.loader.content.provider.impl.DuckDuckGoContentProvider;
import com.zephyr.scraper.loader.content.provider.impl.YandexContentProvider;
import com.zephyr.scraper.loader.internal.AgentResponse;
import com.zephyr.scraper.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ResponseExtractorImpl implements ResponseExtractor {
    private static final String MEDIA_TYPE_HEADER = "Content-Type";
    private static final String TEXT_HTML_VALUE = "text/html";

    private Map<SearchEngine, ContentProvider> providers;

    @PostConstruct
    public void init() {
        providers = ImmutableMap.<SearchEngine, ContentProvider>builder()
                .put(SearchEngine.DUCKDUCKGO, new DuckDuckGoContentProvider())
                .put(SearchEngine.YANDEX, new YandexContentProvider())
                .build();
    }

    @Override
    public PageResponse extract(AgentResponse response, SearchEngine engine, int number) {
        String type = getType(response);
        return PageResponse.of(type, parse(type, engine, response.getContent()), number);
    }

    private String getType(AgentResponse response) {
        return Optional.ofNullable(response.getHeaders().get(MEDIA_TYPE_HEADER)).stream()
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

    private Object parse(String type, SearchEngine engine, String content) {
        if (TEXT_HTML_VALUE.equals(type)) {
            return Jsoup.parse(content);
        }

        return MapUtils.getOrThrow(providers, engine);
    }
}