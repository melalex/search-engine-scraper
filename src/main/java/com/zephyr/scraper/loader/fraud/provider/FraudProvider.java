package com.zephyr.scraper.loader.fraud.provider;

import com.zephyr.scraper.domain.PageResponse;

public interface FraudProvider {

    boolean provide(PageResponse pageResponse);
}
