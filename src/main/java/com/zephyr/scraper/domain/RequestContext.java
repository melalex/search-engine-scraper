package com.zephyr.scraper.domain;

import com.zephyr.scraper.domain.external.Proxy;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Delegate;

import java.time.Duration;

@Value
@Builder
public class RequestContext {

    @Delegate
    private EngineRequest engineRequest;

    private Proxy proxy;
    private Duration duration;
}