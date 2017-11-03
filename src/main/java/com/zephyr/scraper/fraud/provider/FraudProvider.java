package com.zephyr.scraper.fraud.provider;

import com.zephyr.scraper.domain.PageResponse;

public interface FraudProvider {

    boolean provide(PageResponse pageResponse);
}
