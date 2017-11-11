package com.zephyr.scraper.domain;

import org.junit.Test;

import static com.zephyr.scraper.internal.PaginationConstants.*;
import static org.junit.Assert.*;

public class PageTest {

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
        assertEquals(FIRST_ROW, FIRST_PAGE.getStart());
        assertEquals(ZERO_BASED_FIRST_ROW, FIRST_ZERO_BASED_PAGE.getStart());
    }

    @Test
    public void shouldGetStartForLastPage() {
        assertEquals(START_OF_LAST, LAST_PAGE.getStart());
        assertEquals(START_OF_ZERO_BASED_LAST, LAST_ZERO_BASED_PAGE.getStart());
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
        assertFalse(FIRST_ZERO_BASED_PAGE.isNotFirst());
    }

    @Test
    public void shouldReturnTrueForNotFirst() {
        assertTrue(LAST_PAGE.isNotFirst());
        assertTrue(LAST_ZERO_BASED_PAGE.isNotFirst());
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
        assertFalse(LAST_PAGE.isNotLast());
        assertFalse(LAST_ZERO_BASED_PAGE.isNotLast());
    }

    @Test
    public void shouldReturnTrueForNotLast() {
        assertTrue(FIRST_PAGE.isNotLast());
        assertTrue(FIRST_ZERO_BASED_PAGE.isNotLast());
    }
}