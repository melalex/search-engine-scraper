package com.zephyr.scraper.utils;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class MapUtils {

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(key)));
    }

    public <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    private <K> String errorMessage(K key) {
        return String.format("Map doesn't contains key '%s'", key);
    }

    public <K, V> Map<K, V> merge(Map<K, V> result, Map<K, V> element) {
        result.putAll(element);
        return result;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<K, V> {
        private final Map<K, V> prototype = new HashMap<>();

        public Builder<K, V> put(K key, V value) {
            prototype.put(key, value);
            return this;
        }

        public Builder<K, V> putIfTrue(K key, V value, boolean condition) {
            if (condition) {
                prototype.put(key, value);
            }
            return this;
        }

        public Builder<K, V> putIfNotNull(K key, V value) {
            if (Objects.nonNull(value)) {
                prototype.put(key, value);
            }
            return this;
        }

        public Map<K, V> build() {
            return ImmutableMap.copyOf(prototype);
        }
    }
}
