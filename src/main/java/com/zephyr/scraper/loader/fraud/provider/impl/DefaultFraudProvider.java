package com.zephyr.scraper.loader.fraud.provider.impl;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.fraud.provider.FraudProvider;

public class DefaultFraudProvider implements FraudProvider {

    @Override
    public boolean provide(PageResponse pageResponse) {
        return false;
    }
}
