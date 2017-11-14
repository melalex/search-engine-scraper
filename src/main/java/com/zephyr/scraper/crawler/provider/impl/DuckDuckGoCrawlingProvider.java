package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DuckDuckGoCrawlingProvider implements CrawlingProvider {

    @Override
    public List<String> provide(EngineResponse engineResponse) {
        return Collections.emptyList();
    }
}