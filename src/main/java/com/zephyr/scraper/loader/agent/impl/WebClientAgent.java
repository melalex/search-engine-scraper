package com.zephyr.scraper.loader.agent.impl;

import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.loader.agent.Agent;
import com.zephyr.scraper.loader.internal.AgentResponse;
import com.zephyr.scraper.loader.exceptions.AgentException;
import lombok.NoArgsConstructor;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@NoArgsConstructor(staticName = "create")
public class WebClientAgent implements Agent {
    private final Map<String, List<String>> headers = new HashMap<>();
    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    private Proxy proxy;


    @Override
    public Agent header(String name, String value) {
        headers.put(name, List.of(value));
        return this;
    }

    @Override
    public Agent header(String name, String value, Supplier<Boolean> condition) {
        if (condition.get()) {
            headers.put(name, List.of(value));
        }

        return this;
    }

    @Override
    public Agent proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public Mono<AgentResponse> get(String uri, Map<String, ?> params) {
        // @formatter:off
        return WebClient.builder()
                    .clientConnector(connector())
                    .baseUrl(uri)
                    .defaultUriVariables(params)
                    .defaultHeaders(h -> h.putAll(headers))
                .build()
                .get()
                    .exchange()
                    .flatMap(c -> Mono.zip(c.bodyToMono(String.class), Mono.just(c.headers().asHttpHeaders())))
                    .map(r -> AgentResponse.of(r.getT1(), r.getT2()))
                    .onErrorMap(t -> new AgentException("Exception during request", t));
        // @formatter:on
    }

    private ClientHttpConnector connector() {
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