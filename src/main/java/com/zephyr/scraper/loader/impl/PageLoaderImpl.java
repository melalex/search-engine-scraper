package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.context.ContextManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PageLoaderImpl implements PageLoader {

    @Setter(onMethod = @__(@Autowired))
    private ContextManager contextManager;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Override
    public Mono<Response> load(Request request) {
        return contextManager.toContext(request)
                .flatMap(c -> browser.browse(c))
                .collectList()
                .map(h -> Response.of(request.getTask(), request.getProvider(), h));
    }
}