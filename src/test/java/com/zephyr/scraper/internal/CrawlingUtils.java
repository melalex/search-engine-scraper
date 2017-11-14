package com.zephyr.scraper.internal;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.external.SearchEngine;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

public final class CrawlingUtils {

    private CrawlingUtils() {

    }

    public static EngineResponse toResponse(String resource, SearchEngine engine) {
        return EngineResponse.of(Collections.emptyMap(), getBody(resource), engine);
    }

    private static String getBody(String resource) {
        try {
            URL url = Resources.getResource(resource);
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}