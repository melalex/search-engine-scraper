package com.zephyr.scraper.browser.composer.enricher.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.browser.composer.enricher.HeadersProvider;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.utils.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AjaxHeadersProvider implements HeadersProvider {
    private static final String ACCEPT = "*/*";

    @Override
    public Map<String, List<String>> provide(RequestContext context) {
        return MapUtils.multiValueMapBuilder()
                .put(HttpHeaders.ACCEPT, ACCEPT)
                .put(HttpHeaders.HOST, context.getBaseUrl())
                .put(HttpHeaders.ORIGIN, context.getBaseUrl())
                .build();
    }
}