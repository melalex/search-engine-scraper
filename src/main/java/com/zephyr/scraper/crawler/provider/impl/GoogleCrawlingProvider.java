package com.zephyr.scraper.crawler.provider.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoogleCrawlingProvider extends AbstractCrawlingProvider {

    @Override
    protected List<String> parse(Document document, String linkSelector) {
        return document
                .select(linkSelector).stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }
}
