package com.zephyr.scraper.browser.composer.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zephyr.scraper.browser.composer.managers.HeadersManager;
import com.zephyr.scraper.domain.RequestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeadersManagerComposerImplTest {
    private static final String FIRST_KEY = "FIRST_KEY";
    private static final List<String> FIRST_VALUE = ImmutableList.of("FIRST_VALUE");
    private static final String SECOND_KEY = "SECOND_KEY";
    private static final List<String> SECOND_VALUE = ImmutableList.of("SECOND_VALUE");

    private static final RequestContext DOES_NOT_MATTER = null;

    @Mock
    private HeadersManager firstManager;

    @Mock
    private HeadersManager secondManager;

    private final Map<String, List<String>> firstHeaders = firstHeaders();
    private final Map<String, List<String>> secondHeaders = secondHeaders();
    private final HeadersManagerComposerImpl testInstance = new HeadersManagerComposerImpl();

    @Before
    public void setUp() {
        when(firstManager.manage(DOES_NOT_MATTER)).thenReturn(firstHeaders);
        when(secondManager.manage(DOES_NOT_MATTER)).thenReturn(secondHeaders);

        testInstance.setManagers(ImmutableList.of(firstManager, secondManager));
    }

    @Test
    public void shouldCompose() {
        Map<String, List<String>> headers = new HashMap<>();

        testInstance.compose(headers, DOES_NOT_MATTER);

        assertTrue(headers.entrySet().containsAll(firstHeaders.entrySet()));
        assertTrue(headers.entrySet().containsAll(secondHeaders.entrySet()));
    }

    private Map<String, List<String>> firstHeaders() {
        return ImmutableMap.<String, List<String>>builder()
                .put(FIRST_KEY, FIRST_VALUE)
                .build();
    }

    private Map<String, List<String>> secondHeaders() {
        return ImmutableMap.<String, List<String>>builder()
                .put(SECOND_KEY, SECOND_VALUE)
                .build();
    }
}