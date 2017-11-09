package com.zephyr.scraper.crawler.fraud.provider;

import org.jsoup.nodes.Document;

@FunctionalInterface
public interface FraudProvider {

    boolean provide(Document document);
}
