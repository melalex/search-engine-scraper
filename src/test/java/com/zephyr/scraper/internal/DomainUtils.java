package com.zephyr.scraper.internal;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.external.CountryDto;
import com.zephyr.scraper.domain.external.Keyword;
import com.zephyr.scraper.domain.external.PlaceDto;
import com.zephyr.scraper.domain.external.SearchEngine;
import org.jsoup.nodes.Document;

import java.util.Collections;

public final class DomainUtils {
    private static final String RESPONSE_BODY = "RESPONSE_BODY";

    public static final RequestContext ANY_REQUEST_CONTEXT = RequestContext.builder().build();
    public static final Document ANY_DOCUMENT = new Document("");
    public static final Keyword ANY_KEYWORD = new Keyword();
    public static final PlaceDto ANY_PLACE = new PlaceDto();
    public static final SearchEngine ANY_PROVIDER = SearchEngine.GOOGLE;

    private DomainUtils() {

    }

    public static RequestContext requestContextWith(String baseUrl) {
        return RequestContext.builder()
                .request(Request.builder().baseUrl(baseUrl).build())
                .build();
    }

    public static RequestContext requestContextWithEngine(SearchEngine engine) {
        return RequestContext.builder()
                .request(Request.builder().provider(engine).build())
                .build();
    }

    public static Request requestWith(SearchEngine engine) {
        return requestWith(ANY_KEYWORD, engine, 0);
    }

    public static Request requestWith(Keyword keyword, SearchEngine engine, int offset) {
        return Request.builder()
                .keyword(keyword)
                .provider(engine)
                .offset(offset)
                .build();
    }

    public static Response responseWith(SearchEngine engine) {
        return Response.of(Collections.emptyMap(), RESPONSE_BODY, engine);
    }

    public static Keyword keywordWith(String word) {
        return keywordWith(word, null);
    }

    public static Keyword keywordWith(String word, String language) {
        Keyword keyword = new Keyword();
        keyword.setWord(word);
        keyword.setLanguageIso(language);

        return keyword;
    }

    public static CountryDto countryWithGoogle(String google) {
        return countryWith(google, null);
    }

    public static CountryDto countryWithYandex(String yandex) {
        return countryWith(null, yandex);
    }

    private static CountryDto countryWith(String google, String yandex) {
        CountryDto country = new CountryDto();
        country.setLocaleGoogle(google);
        country.setLocaleYandex(yandex);

        return country;
    }
}