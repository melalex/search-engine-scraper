package com.zephyr.scraper.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {
    private static final int FIRST = 1;
    private static final int ZERO_BASED_FIRST = 0;
    private static final int START_OF_FINAL = 81;
    private static final int START_OF_ZERO_BASED_FINAL = 80;
    private static final int FIRST_PAGE_NUMBER = 0;
    private static final int SECOND_PAGE_NUMBER = FIRST_PAGE_NUMBER + 1;
    private static final int LAST_PAGE_NUMBER = 4;
    private static final int OUT_BOUND_PAGE_NUMBER = LAST_PAGE_NUMBER + 1;
    private static final int PAGE_SIZE = 20;
    private static final int LAST_PAGE_SIZE = 10;
    private static final int COUNT = 90;

    private static final Page FIRST_PAGE = Page.of(FIRST, FIRST_PAGE_NUMBER, PAGE_SIZE, COUNT);
    private static final Page FIRST_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST, FIRST_PAGE_NUMBER, PAGE_SIZE, COUNT);

    private static final Page SECOND_PAGE = Page.of(FIRST, SECOND_PAGE_NUMBER, PAGE_SIZE, COUNT);
    private static final Page SECOND_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST, SECOND_PAGE_NUMBER, PAGE_SIZE, COUNT);

    private static final Page LAST_PAGE = Page.of(FIRST, LAST_PAGE_NUMBER, LAST_PAGE_SIZE, COUNT);
    private static final Page LAST_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST, LAST_PAGE_NUMBER, LAST_PAGE_SIZE, COUNT);

    private static final Page OUT_BOUND_PAGE = Page.of(FIRST, OUT_BOUND_PAGE_NUMBER, LAST_PAGE_SIZE, COUNT);
    private static final Page OUT_BOUND_ZERO_BASED_PAGE = Page.of(ZERO_BASED_FIRST, OUT_BOUND_PAGE_NUMBER, LAST_PAGE_SIZE, COUNT);

    @Test
    public void shouldGetPageSizeForFirstPage() {
        assertEquals(PAGE_SIZE, FIRST_PAGE.getPageSize());
        assertEquals(PAGE_SIZE, FIRST_ZERO_BASED_PAGE.getPageSize());
    }

    @Test
    public void shouldGetPageSizeForLastPage() {
        assertEquals(LAST_PAGE_SIZE, LAST_PAGE.getPageSize());
        assertEquals(LAST_PAGE_SIZE, LAST_ZERO_BASED_PAGE.getPageSize());
    }

    @Test
    public void shouldGetStartForFirstPage() {
        assertEquals(FIRST, FIRST_PAGE.getStart());
        assertEquals(ZERO_BASED_FIRST, FIRST_ZERO_BASED_PAGE.getStart());
    }

    @Test
    public void shouldGetStartForLastPage() {
        assertEquals(START_OF_FINAL, LAST_PAGE.getStart());
        assertEquals(START_OF_ZERO_BASED_FINAL, LAST_ZERO_BASED_PAGE.getStart());
    }

    @Test
    public void shouldGetLastPage() {
        assertEquals(LAST_PAGE_NUMBER, FIRST_PAGE.getLastPage());
        assertEquals(LAST_PAGE_NUMBER, FIRST_ZERO_BASED_PAGE.getLastPage());
    }

    @Test
    public void shouldReturnTrueForFirst() {
        assertTrue(FIRST_PAGE.isFirst());
        assertTrue(FIRST_ZERO_BASED_PAGE.isFirst());
    }

    @Test
    public void shouldReturnFalseForNotFirst() {
        assertFalse(LAST_PAGE.isFirst());
        assertFalse(LAST_ZERO_BASED_PAGE.isFirst());
    }

    @Test
    public void shouldReturnFalseForFirst() {
        assertFalse(FIRST_PAGE.isNotFirst());
        assertFalse(FIRST_ZERO_BASED_PAGE.isFirst());
    }

    @Test
    public void shouldReturnTrueForNotFirst() {
        assertTrue(LAST_PAGE.isNotFirst());
        assertTrue(LAST_ZERO_BASED_PAGE.isFirst());
    }

    @Test
    public void shouldReturnTrueForLast() {
        assertTrue(LAST_PAGE.isLast());
        assertTrue(LAST_ZERO_BASED_PAGE.isLast());
    }

    @Test
    public void shouldReturnFalseForNotLast() {
        assertFalse(FIRST_PAGE.isLast());
        assertFalse(FIRST_ZERO_BASED_PAGE.isLast());
    }

    @Test
    public void shouldReturnFalseForLast() {
        assertFalse(LAST_PAGE.isNotFirst());
        assertFalse(LAST_ZERO_BASED_PAGE.isNotLast());
    }

    @Test
    public void shouldReturnTrueForNotLast() {
        assertTrue(FIRST_PAGE.isNotFirst());
        assertTrue(FIRST_ZERO_BASED_PAGE.isNotLast());
    }

    @Test
    public void shouldGetNextPage() {
        assertEquals(SECOND_PAGE, FIRST_PAGE.getNextPage());
        assertEquals(SECOND_ZERO_BASED_PAGE, FIRST_ZERO_BASED_PAGE.getNextPage());
    }

    @Test
    public void shouldReturnTrueOnFirstPage() {
        assertTrue(FIRST_PAGE.hasNextPage());
        assertTrue(FIRST_ZERO_BASED_PAGE.hasNextPage());
    }

    @Test
    public void shouldReturnTrueOnLastPage() {
        assertTrue(LAST_PAGE.hasNextPage());
        assertTrue(LAST_ZERO_BASED_PAGE.hasNextPage());
    }

    @Test
    public void shouldReturnFalseOnPageAfterLast() {
        assertTrue(OUT_BOUND_PAGE.hasNextPage());
        assertTrue(OUT_BOUND_ZERO_BASED_PAGE.hasNextPage());
    }
}