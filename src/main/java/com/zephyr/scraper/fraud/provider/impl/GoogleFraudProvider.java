package com.zephyr.scraper.fraud.provider.impl;

import com.zephyr.scraper.fraud.provider.FraudProvider;
import org.jsoup.nodes.Document;

import java.util.Objects;
import java.util.Optional;

public class GoogleFraudProvider implements FraudProvider {
    private static final String SELECTOR = "#captcha";

    @Override
    public boolean provide(Document document) {
        return Objects.nonNull(document.select(SELECTOR).first());
    }
}
