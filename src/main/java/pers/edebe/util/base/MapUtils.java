package pers.edebe.util.base;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtils {
    @SafeVarargs
    public static <K, V> Map<K, V> merge(Map<? extends K, ? extends V>... maps) {
        Map<K, V> map = new LinkedHashMap<>();
        Arrays.stream(maps).forEach(map::putAll);
        return map;
    }
}