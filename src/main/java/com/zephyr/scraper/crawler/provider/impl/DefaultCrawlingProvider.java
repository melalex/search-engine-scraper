package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCrawlingProvider extends AbstractCrawlingProvider {
    private static final String HREF_ATTR = "href";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Override
    protected List<String> parse(Response response) {
        String linkSelector = scraperProperties
                .getScraper(response.getProvider())
                .getLinkSelector();

        return response.getDocuments().stream()
                .sorted(Comparator.comparingInt(PageResponse::getNumber))
                .map(p -> p.getContent(Document.class))
                .flatMap(d -> d.select(linkSelector).stream())
                .map(e -> e.attr(HREF_ATTR))
                .collect(Collectors.toList());
    }
}