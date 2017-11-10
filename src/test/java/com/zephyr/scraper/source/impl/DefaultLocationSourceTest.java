package com.zephyr.scraper.source.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultLocationSourceTest {
    private static final String ANY_ISO = null;
    private static final String ANY_NAME = null;

    private final DefaultLocationSource testInstance = new DefaultLocationSource();

    @Before
    public void setUp() {
        testInstance.init();
    }

    @Test
    public void shouldFindPlace() {
        assertNotNull(testInstance.findPlace(ANY_ISO, ANY_NAME));
    }
}