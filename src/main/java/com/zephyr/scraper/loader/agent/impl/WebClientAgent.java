package com.zephyr.scraper.loader.agent.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.loader.agent.Agent;
import com.zephyr.scraper.loader.agent.model.AgentResponse;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.loader.exceptions.AgentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class WebClientAgent implements Agent {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Override
    public Mono<AgentResponse> get(RequestContext context) {
        log.info("Creating Agent for Task {} and Engine {} on {} page",
                context.getTaskId(), context.getProvider(), context.getNumber());

        // @formatter:off
        return WebClient.builder()
                    .clientConnector(connector(context.getProxy()))
                    .baseUrl(context.getFullUrl())
                    .defaultUriVariables(context.getParams())
                    .defaultHeader(HttpHeaders.REFERER, context.getBaseUrl())
                    .defaultHeader(HttpHeaders.USER_AGENT, context.getUserAgent())
                    .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, context.getLanguageIso())
                    .defaultHeader(HttpHeaders.ACCEPT, ACCEPT)
                    .defaultHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                    .defaultHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                    .defaultHeader(HttpHeaders.UPGRADE, TRUE)
                    .defaultHeader(DO_NOT_TRACK, TRUE)
                    .defaultHeader(UPGRADE_INSECURE_REQUESTS, TRUE)
                .build()
                .get()
                    .exchange()
                    .flatMap(c -> Mono.zip(c.bodyToMono(String.class), Mono.just(c.headers().asHttpHeaders())))
                    .map(r -> AgentResponse.of(r.getT1(), r.getT2()))
                    .onErrorMap(t -> new AgentException("Exception during request", t));
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