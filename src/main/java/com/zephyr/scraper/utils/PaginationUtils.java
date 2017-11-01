package com.zephyr.scraper.utils;

import lombok.experimental.UtilityClass;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;

@UtilityClass
public class PaginationUtils {

    public Stream<Integer> pagesStream(int count, int perPage) {
        return IntStream.range(0, roundUp(count, perPage)).boxed();
    }

    public int startOfZeroBased(int page, int perPage) {
        return page * perPage;
    }

    public int startOf(int page, int perPage) {
        return startOfZeroBased(page, perPage) + 1;
    }

    public boolean isNotFirstZeroBased(int start) {
        return start != 0;
    }

    public boolean isNotFirst(int start) {
        return start != 1;
    }

    private int roundUp(int num, int divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }
}
