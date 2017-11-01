package com.zephyr.scraper.loader.fraud.provider.impl;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.fraud.provider.FraudProvider;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class GoogleFraudProvider implements FraudProvider {
    private static final String SELECTOR = "body > div > div";
    private static final String ERROR_MESSAGE = "Our systems have detected unusual traffic from your computer network";

    @Override
    public boolean provide(PageResponse page) {
        return Optional.ofNullable(page.getContent(Document.class).select(SELECTOR).first())
                .stream()
                .map(Element::text)
                .anyMatch(t -> t.startsWith(ERROR_MESSAGE));
    }
}
