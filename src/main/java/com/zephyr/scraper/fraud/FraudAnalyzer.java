package com.zephyr.scraper.fraud;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.RequestContext;

public interface FraudAnalyzer {

    void analyze(RequestContext context, PageResponse response);
}
