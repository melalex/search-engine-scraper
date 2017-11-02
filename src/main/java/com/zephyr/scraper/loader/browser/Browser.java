package com.zephyr.scraper.loader.browser;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.context.model.RequestContext;
import reactor.core.publisher.Mono;

public interface Browser {

    Mono<PageResponse> browse(RequestContext requestContext);
}
