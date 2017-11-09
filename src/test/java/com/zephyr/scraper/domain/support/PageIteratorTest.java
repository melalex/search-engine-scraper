package com.zephyr.scraper.domain.support;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.domain.Page;
import org.junit.Test;

import java.util.List;

import static com.zephyr.scraper.internal.PaginationConstants.*;
import static org.junit.Assert.assertEquals;

public class PageIteratorTest {

    @Test
    public void shouldIterate() {
        assertEquals(expected(), ImmutableList.copyOf(PageIterator.of(FIRST_PAGE)));
    }

    private List<Page> expected() {
        return ImmutableList.of(FIRST_PAGE, SECOND_PAGE, THIRD_PAGE, FORTH_PAGE, LAST_PAGE);
    }
}