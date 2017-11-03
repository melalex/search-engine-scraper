package com.zephyr.scraper.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Page {
    private int first;
    private int page;
    private int pageSize;
    private int count;

    public int getPageSize() {
        return isNotLast() ? pageSize : count - getStart();
    }

    public int getStart() {
        return first + page * pageSize;
    }

    public boolean isFirst() {
        return getStart() == first;
    }

    public boolean isNotFirst() {
        return !isFirst();
    }

    public boolean isLast() {
        return getStart() + pageSize >= count;
    }

    public boolean isNotLast() {
        return !isLast();
    }
}