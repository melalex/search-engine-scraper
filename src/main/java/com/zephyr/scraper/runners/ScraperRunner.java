package com.zephyr.scraper.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.SearchResult;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.saver.FileSaver;
import lombok.Cleanup;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class ScraperRunner implements CommandLineRunner {
    private static final String DIRECTORY = "results";

    @Setter(onMethod = @__(@Autowired))
    private ScrapingFlow scrapingFlow;

    @Setter(onMethod = @__(@Autowired))
    private FileSaver fileSaver;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(DIRECTORY));
    }

    @Override
    public void run(String... args) {
        if (ArrayUtils.isNotEmpty(args)) {
            scrapingFlow.handle(keywords(args[0]))
                    .subscribe(this::writeResult);
        } else {
            log.error("File with Keywords should be provided");
        }
    }

    @SneakyThrows
    private Flux<Keyword> keywords(String path) {
        log.info("Loading Keywords...");

        @Cleanup
        CSVParser parser = CSVParser.parse(new File(path), Charset.defaultCharset(), CSVFormat.DEFAULT);

        return Flux.fromIterable(parser.getRecords())
                .map(this::toKeyword);
    }


    private void writeResult(SearchResult searchResult) {
        log.info("Received SearchResult for Keyword {}, search engine {} and offset {}",
                searchResult.getKeyword(), searchResult.getProvider(), searchResult.getOffset());

        try {
            fileSaver.save(path(searchResult), mapper.writeValueAsBytes(searchResult));
        } catch (IOException e) {
            log.error("Exception while Serializing to json", e);
        }
    }

    private Path path(SearchResult searchResult) {
        String fileName = String.format("%s-%s-%s.json",
                searchResult.getKeyword().getWord(), searchResult.getProvider(), searchResult.getTimestamp());
        return Paths.get(DIRECTORY, fileName);
    }

    private Keyword toKeyword(CSVRecord record) {
        Keyword keyword = new Keyword();
        keyword.setWord(record.get(0));
        keyword.setPlace(record.get(1));
        keyword.setCountryIso(record.get(2));
        keyword.setLanguageIso(record.get(3));
        keyword.setUserAgent(record.get(4));

        return keyword;
    }
}
