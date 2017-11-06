package com.zephyr.scraper.browser.composer.managers;

import com.zephyr.scraper.domain.RequestContext;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HeadersManager {

    Map<String, List<String>> manage(RequestContext context);
}
