package com.zephyr.scraper.browser.composer;

import com.zephyr.scraper.domain.RequestContext;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HeadersManagerComposer {

    Map<String, List<String>> compose(RequestContext context);
}