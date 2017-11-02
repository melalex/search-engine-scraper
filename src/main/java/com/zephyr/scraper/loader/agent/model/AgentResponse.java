package com.zephyr.scraper.loader.agent.model;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value(staticConstructor = "of")
public class AgentResponse {
    private String content;
    private Map<String, List<String>> headers;
}