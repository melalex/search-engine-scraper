package com.zephyr.scraper.loader.agent.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.scraper.loader.internal.RequestContext;
import com.zephyr.scraper.loader.agent.Agent;
import com.zephyr.scraper.loader.agent.AgentFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebClientAgentFactory implements AgentFactory {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    @Override
    public Agent create(RequestContext context) {
        log.info("Creating Agent for TaskDto {} and Engine {} on {} page",
                context.getTask().getId(), context.getProvider(), context.getPage().getNumber());

        return WebClientAgent.create()
                .proxy(context.getProxy())
                .header(HttpHeaders.REFERER, context.getBaseUrl())
                .header(HttpHeaders.USER_AGENT, context.getTask().getUserAgent())
                .header(HttpHeaders.ACCEPT_LANGUAGE, context.getTask().getLanguageIso())
                .header(HttpHeaders.ACCEPT, ACCEPT)
                .header(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .header(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .header(HttpHeaders.UPGRADE, TRUE)
                .header(DO_NOT_TRACK, TRUE)
                .header(UPGRADE_INSECURE_REQUESTS, TRUE);
    }
}