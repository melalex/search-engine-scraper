package com.zephyr.scraper.internal;

import java.time.*;

import static org.mockito.Mockito.when;

public final class TimeUtils {
    private static final long CURRENT_TIME = 1510134473134L;
    private static final Instant INSTANT = Instant.ofEpochMilli(CURRENT_TIME);
    private static final ZoneId ZONE_ID = ZoneOffset.UTC;

    private TimeUtils() {

    }

    public static void configureClock(Clock clock) {
        when(clock.instant()).thenReturn(INSTANT);
        when(clock.getZone()).thenReturn(ZONE_ID);
    }

    public static LocalDateTime now() {
        return LocalDateTime.ofInstant(INSTANT, ZONE_ID);
    }
}
