package com.zephyr.scraper.loader.fraud.impl;

import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.domain.SearchEngine;
import com.zephyr.scraper.loader.exceptions.FraudException;
import com.zephyr.scraper.loader.fraud.FraudAnalyzer;
import com.zephyr.scraper.loader.fraud.manager.FraudManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FraudAnalyzerImpl implements FraudAnalyzer {

    @Setter(onMethod = @__(@Autowired))
    private FraudManager manager;

    @Override
    public void analyze(RequestContext context, PageResponse response) {
        int page = context.getNumber();
        String task = context.getTaskId();
        SearchEngine engine = context.getProvider();

        if (manager.manage(engine).provide(response)) {
            String message = String.format("Fraud detection when scraping %s for TaskDto %s on %s page", engine, task, page);

            log.error(message);

            throw new FraudException(message);
        }

        log.info("Fraud analyze passed for TaskDto {} and Engine {} on {} page", task, engine, page);
    }
}
