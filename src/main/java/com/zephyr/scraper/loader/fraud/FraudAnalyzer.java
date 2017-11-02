package com.zephyr.scraper.loader.fraud;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.context.model.RequestContext;

public interface FraudAnalyzer {

    void analyze(RequestContext context, PageResponse response);
}
