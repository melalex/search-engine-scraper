package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.crawler.fraud.FraudAnalyzer;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AbstractCrawlingProvider implements CrawlingProvider {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Override
    public List<String> provide(EngineResponse engineResponse) {
        SearchEngine engine = engineResponse.getProvider();
        Document document = Jsoup.parse(engineResponse.getBody());
        String linkSelector = scraperProperties
                .getScraper(engine)
                .getLinkSelector();

        fraudAnalyzer.analyze(engine, document);

        return parse(document, linkSelector);
    }

    protected abstract List<String> parse(Document document, String linkSelector);
}