package com.zephyr.scraper.internal;

import com.zephyr.scraper.domain.Page;

public final class PaginationConstants {
    public static final int FIRST_ROW = 1;
    public static final int ZERO_BASED_FIRST_ROW = 0;

    public static final int START_OF_LAST = 81;
    public static final int START_OF_ZERO_BASED_LAST = 80;

    public static final int FIRST_PAGE_NUMBER = 0;
    public static final int SECOND_PAGE_NUMBER = FIRST_PAGE_NUMBER + 1;
    public static final int LAST_PAGE_NUMBER = 4;
    public static final int OUT_BOUND_PAGE_NUMBER = LAST_PAGE_NUMBER + 1;

    public static final int PAGE_SIZE = 20;
    public static final int LAST_PAGE_SIZE = 10;
    public static final int ROW_COUNT = 90;

    public static final Page FIRST_PAGE = Page.of(FIRST_ROW, FIRST_PAGE_NUMBER, PAGE_SIZE, ROW_COUNT);
    public static final Page FIRST_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST_ROW, FIRST_PAGE_NUMBER, PAGE_SIZE, ROW_COUNT);

    public static final Page SECOND_PAGE = Page.of(FIRST_ROW, SECOND_PAGE_NUMBER, PAGE_SIZE, ROW_COUNT);
    public static final Page SECOND_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST_ROW, SECOND_PAGE_NUMBER, PAGE_SIZE, ROW_COUNT);

    public static final Page LAST_PAGE = Page.of(FIRST_ROW, LAST_PAGE_NUMBER, LAST_PAGE_SIZE, ROW_COUNT);
    public static final Page LAST_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST_ROW, LAST_PAGE_NUMBER, LAST_PAGE_SIZE, ROW_COUNT);

    public static final Page OUT_BOUND_PAGE = Page.of(FIRST_ROW, OUT_BOUND_PAGE_NUMBER, LAST_PAGE_SIZE, ROW_COUNT);
    public static final Page OUT_BOUND_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST_ROW, OUT_BOUND_PAGE_NUMBER, LAST_PAGE_SIZE, ROW_COUNT);

    private PaginationConstants() {

    }
}
