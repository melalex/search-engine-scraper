package com.zephyr.scraper.crawler.fraud.provider.impl;

import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import org.jsoup.nodes.Document;

public class DefaultFraudProvider implements FraudProvider {

    @Override
    public boolean provide(Document document) {
        return false;
    }
}
