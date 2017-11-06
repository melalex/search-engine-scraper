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

    public int getLastPage() {
        return (int) Math.ceil(count / pageSize) - 1;
    }

    public boolean isFirst() {
        return page == 0;
    }

    public boolean isNotFirst() {
        return !isFirst();
    }

    public boolean isLast() {
        return getLastPage() == page;
    }

    public boolean isNotLast() {
        return !isLast();
    }

    public Page getNextPage() {
        return Page.of(first, page + 1, pageSize, count);
    }

    public boolean hasNextPage() {
        return page > getLastPage();
    }
}