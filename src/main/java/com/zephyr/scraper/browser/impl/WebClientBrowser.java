package com.zephyr.scraper.browser.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.browser.composer.HeadersManagerComposer;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.exceptions.BrowserException;
import com.zephyr.scraper.domain.external.Proxy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class WebClientBrowser implements Browser {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Setter(onMethod = @__(@Autowired))
    private HeadersManagerComposer headersManagerComposer;

    @Override
    public Mono<Response> get(RequestContext context) {
        log.info("Creating Browser for Keyword {} and Engine {} on {} offset",
                context.getKeyword(), context.getProvider(), context.getOffset());

        // @formatter:off
        return WebClient.builder()
                    .clientConnector(connector(context.getProxy()))
                    .baseUrl(context.getBaseUrl())
                    .defaultUriVariables(context.getParams())
                    .defaultHeaders(h -> headersManagerComposer.compose(h, context))
                    .defaultHeader(HttpHeaders.REFERER, context.getBaseUrl())
                    .defaultHeader(HttpHeaders.USER_AGENT, context.getUserAgent())
                    .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, context.getLanguageIso())
                    .defaultHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                    .defaultHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                    .defaultHeader(DO_NOT_TRACK, TRUE)
                .build()
                .get()
                    .uri(context.getUri())
                    .exchange()
                    .flatMap(c -> Mono.zip(Mono.just(c.headers().asHttpHeaders()), c.bodyToMono(String.class)))
                    .map(r -> Response.of(r.getT1(), r.getT2(), context.getProvider()))
                    .onErrorMap(t -> new BrowserException("Exception during request", t));
        // @formatter:on
    }

    private ClientHttpConnector connector(Proxy proxy) {
        if (Objects.nonNull(proxy)) {
            return new ReactorClientHttpConnector(b -> b.httpProxy(
                    a -> a.host(proxy.getIp())
                            .port(proxy.getPort())
                            .username(proxy.getUsername())
                            .password(u -> proxy.getPassword())
            ));
        }

        return directConnector;
    }
}