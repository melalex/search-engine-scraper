package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.domain.RequestContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AjaxHeadersProvider implements HeadersProvider {
    private static final String ACCEPT = "*/*";

    @Override
    public Map<String, List<String>> provide(RequestContext context) {
        return ImmutableMap.<String, List<String>>builder()
                .put(HttpHeaders.ACCEPT, ImmutableList.of(ACCEPT))
                .put(HttpHeaders.HOST, ImmutableList.of(context.getBaseUrl()))
                .put(HttpHeaders.ORIGIN, ImmutableList.of(context.getBaseUrl()))
                .build();
    }
}