package com.zephyr.scraper.loader.browser;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.Request;
import reactor.core.publisher.Mono;

public interface Browser {

    Mono<PageResponse> browse(Request request, PageRequest page);
}
