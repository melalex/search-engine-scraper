package com.zephyr.scraper.saver.impl;

import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.saver.FileSaver;
import com.zephyr.scraper.saver.ResponseSaver;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ResponseSaverImpl implements ResponseSaver {
    private static final String DIRECTORY = "responses";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private FileSaver fileSaver;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(DIRECTORY));
    }

    @Override
    public void save(EngineResponse response) {
        if (scraperProperties.getScraper(response.getProvider()).isSaveResponse()) {
            fileSaver.save(path(response), response.getBody().getBytes());
        }
    }

    private Path path(EngineResponse response) {
        String fileName = String.format("%s-%s.html", response.getProvider(), LocalDateTime.now(clock));
        return Paths.get(DIRECTORY, fileName);
    }
}
