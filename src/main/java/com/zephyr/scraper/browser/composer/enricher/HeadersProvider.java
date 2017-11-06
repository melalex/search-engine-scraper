package com.zephyr.scraper.browser.composer.enricher;

import com.zephyr.scraper.domain.RequestContext;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HeadersProvider {

    Map<String, List<String>> provide(RequestContext context);
}
