package com.zephyr.scraper.browser.impl;

import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.browser.composer.HeadersManagerComposer;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.external.Proxy;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.Setter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.proxy.ProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AsyncBrowser implements Browser {
    private static final String REFERER = "Referer";
    private static final String USER_AGENT = "User-Agent";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String CONNECTION = "Connection";
    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    @Setter(onMethod = @__(@Autowired))
    private HeadersManagerComposer headersManagerComposer;

    @Setter(onMethod = @__(@Autowired))
    private AsyncHttpClient asyncHttpClient;

    @Setter(onMethod = @__(@Autowired))
    private Converter<Proxy, ProxyServer> proxyConverter;

    @Override
    public Mono<EngineResponse> get(RequestContext context) {
        Request request = new RequestBuilder()
                .setProxyServer(proxyConverter.convert(context.getProxy()))
                .setFollowRedirect(true)
                .setUrl(context.getFullUrl())
//                .setQueryParams(context.getParams())
                .setHeaders(headersManagerComposer.compose(context))
                .setHeader(REFERER, context.getBaseUrl())
                .setHeader(USER_AGENT, context.getUserAgent())
                .setHeader(ACCEPT_LANGUAGE, context.getLanguageIso())
                .setHeader(ACCEPT_ENCODING, ENCODING)
                .setHeader(CONNECTION, KEEP_ALIVE)
                .setHeader(DO_NOT_TRACK, TRUE)
                .build();

        return Mono.fromFuture(asyncHttpClient.executeRequest(request).toCompletableFuture())
                .map(r -> EngineResponse.of(getHeaders(r.getHeaders()), r.getResponseBody(), context.getProvider()))
                .onErrorMap(t -> new BrowserException("Exception during request", t));
    }

    private Map<String, List<String>> getHeaders(HttpHeaders headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> headers.getAll(e.getKey())));
    }
}
