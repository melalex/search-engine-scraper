package com.zephyr.scraper.browser.composer;

import com.zephyr.scraper.domain.RequestContext;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HeadersManagerComposer {

    void compose(Map<String, List<String>> headers, RequestContext context);
}