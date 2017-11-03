package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.fraud.FraudAnalyzer;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCrawlingProvider implements CrawlingProvider {
    private static final String HREF_ATTR = "href";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Override
    public List<String> provide(Response response) {
        SearchEngine engine = response.getProvider();
        Document document = Jsoup.parse(response.getBody());
        String linkSelector = scraperProperties
                .getScraper(response.getProvider())
                .getLinkSelector();

        fraudAnalyzer.analyze(engine, document);

        return document
                .select(linkSelector).stream()
                .map(e -> e.attr(HREF_ATTR))
                .collect(Collectors.toList());
    }
}