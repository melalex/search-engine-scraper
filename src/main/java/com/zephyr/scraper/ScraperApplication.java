package com.zephyr.scraper;

import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.flow.impl.ScrapingFlowImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }

    @Bean
    public ScrapingFlow scrapingFlow() {
        return new ScrapingFlowImpl();
    }
}