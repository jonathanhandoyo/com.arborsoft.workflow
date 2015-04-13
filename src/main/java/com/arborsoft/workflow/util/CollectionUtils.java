package com.arborsoft.workflow.util;

import java.util.HashMap;
import java.util.Map;

public class CollectionUtils {
    @SafeVarargs
    public static <K, V> Map<K, V> map(Pair<K, V>... pairs) {
        Map<K, V> result = new HashMap<K, V>();
        for (Pair<K, V> pair: pairs) {
            if (pair.valid()) {
                result.put(pair.key(), pair.value());
            }
        }
        return result;
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<>();
    }
}
