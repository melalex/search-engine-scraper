package com.zephyr.scraper.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class PageResponse {
    private String mediaType;
    private Object content;
    private int number;

    public <T> T getContent(Class<T> clazz) {
        if (clazz.isInstance(content)) {
            return clazz.cast(content);
        } else {
            String message = String.format("content type is '%s' not '%s'", content.getClass(), clazz);
            throw new IllegalStateException(message);
        }
    }
}