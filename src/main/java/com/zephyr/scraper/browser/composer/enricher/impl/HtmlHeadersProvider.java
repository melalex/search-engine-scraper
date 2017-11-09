package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.domain.RequestContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HtmlHeadersProvider implements HeadersProvider {
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String TRUE = "1";

    @Override
    public Map<String, List<String>> provide(RequestContext context) {
        return ImmutableMap.<String, List<String>>builder()
                .put(HttpHeaders.ACCEPT, ImmutableList.of(ACCEPT))
                .put(UPGRADE_INSECURE_REQUESTS, ImmutableList.of(TRUE))
                .build();
    }
}