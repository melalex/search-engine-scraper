package com.zephyr.scraper.internal;

import com.zephyr.scraper.domain.Page;

public final class PaginationConstants {
    private static final int FIRST_PAGE_NUMBER = 0;
    private static final int SECOND_PAGE_NUMBER = 1;
    private static final int THIRD_PAGE_NUMBER = 2;
    private static final int FORTH_PAGE_NUMBER = 3;

    public static final int FIRST_ROW = 1;
    public static final int ZERO_BASED_FIRST_ROW = 0;

    public static final int START_OF_LAST = 81;
    public static final int START_OF_ZERO_BASED_LAST = 80;

    public static final int LAST_PAGE_NUMBER = 4;

    public static final int PAGE_SIZE = 20;
    public static final int LAST_PAGE_SIZE = 10;
    public static final int ROW_COUNT = 90;

    public static final Page FIRST_PAGE = Page.of(FIRST_PAGE_NUMBER, FIRST_ROW, PAGE_SIZE, ROW_COUNT);
    public static final Page FIRST_ZERO_BASED_PAGE = Page.of(FIRST_PAGE_NUMBER, ZERO_BASED_FIRST_ROW, PAGE_SIZE, ROW_COUNT);

    public static final Page SECOND_PAGE = Page.of(SECOND_PAGE_NUMBER, FIRST_ROW, PAGE_SIZE, ROW_COUNT);
    public static final Page THIRD_PAGE = Page.of(THIRD_PAGE_NUMBER, FIRST_ROW, PAGE_SIZE, ROW_COUNT);
    public static final Page FORTH_PAGE = Page.of(FORTH_PAGE_NUMBER, FIRST_ROW, PAGE_SIZE, ROW_COUNT);

    public static final Page LAST_PAGE = Page.of(LAST_PAGE_NUMBER, FIRST_ROW, PAGE_SIZE, ROW_COUNT);
    public static final Page LAST_ZERO_BASED_PAGE = Page.of(LAST_PAGE_NUMBER, ZERO_BASED_FIRST_ROW, PAGE_SIZE, ROW_COUNT);

    private PaginationConstants() {

    }
}