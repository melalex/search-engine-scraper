package com.zephyr.scraper.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MapUtilsTest {
    private static final String FIRST_KEY = "key1";
    private static final String FIRST_VALUE = "value1";
    private static final String SECOND_KEY = "key2";

    private static final Map<String, String> TEST_MAP = ImmutableMap.of(FIRST_KEY, FIRST_VALUE);

    @Test
    public void shouldGet() {
        assertEquals(FIRST_VALUE, MapUtils.getOrThrow(TEST_MAP, FIRST_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow() {
        MapUtils.getOrThrow(TEST_MAP, SECOND_KEY);
    }

    @Test
    public void shouldPut() {
        assertTrue(MapUtils.builder().put(FIRST_KEY, FIRST_VALUE).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldPutIfTrue() {
        assertTrue(MapUtils.builder().putIfTrue(FIRST_KEY, FIRST_VALUE, true).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldNotPutIfFalse() {
        assertFalse(MapUtils.builder().putIfTrue(FIRST_KEY, FIRST_VALUE, false).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldPutIfNotNull() {
        assertTrue(MapUtils.builder().putIfNotNull(FIRST_KEY, FIRST_VALUE).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldNotPutIfNull() {
        assertFalse(MapUtils.builder().putIfNotNull(FIRST_KEY, FIRST_VALUE).build().containsKey(FIRST_KEY));
    }
}