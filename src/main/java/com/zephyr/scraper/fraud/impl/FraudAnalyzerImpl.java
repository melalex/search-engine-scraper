package com.zephyr.scraper.fraud.impl;

import com.zephyr.scraper.domain.exceptions.FraudException;
import com.zephyr.scraper.domain.external.SearchEngine;
import com.zephyr.scraper.fraud.FraudAnalyzer;
import com.zephyr.scraper.fraud.manager.FraudManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FraudAnalyzerImpl implements FraudAnalyzer {

    @Setter(onMethod = @__(@Autowired))
    private FraudManager manager;

    @Override
    public void analyze(SearchEngine engine, Document document) {
        if (manager.manage(engine).provide(document)) {
            throw new FraudException();
        }
    }
}
