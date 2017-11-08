package com.zephyr.scraper.properties;

import com.zephyr.scraper.domain.external.SearchEngine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties
public class ScraperProperties {

    private BrowserProperties browser;
    private Map<SearchEngine, EngineProperties> scraper;

    public EngineProperties getScraper(SearchEngine engine) {
        return scraper.get(engine);
    }

    @Data
    public static class EngineProperties {
        private boolean enabled;
        private boolean useProxy;
        private long delay;
        private long errorDelay;
        private int first;
        private int resultCount;
        private int pageSize;
        private String linkSelector;
    }

    @Data
    public static class BrowserProperties {
        private long backoff;
        private int retryCount;
    }
}