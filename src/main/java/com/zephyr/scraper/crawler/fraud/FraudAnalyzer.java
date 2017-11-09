package com.zephyr.scraper.crawler.fraud;

import com.zephyr.scraper.domain.external.SearchEngine;
import org.jsoup.nodes.Document;

@FunctionalInterface
public interface FraudAnalyzer {

    void analyze(SearchEngine engine, Document document);
}
