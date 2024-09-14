package be.cloudns.edebe.util.base;

import lombok.experimental.UtilityClass;
import be.cloudns.edebe.util.collect.ArrayListMap;

import java.util.Map;

@UtilityClass
public class CollectionUtils {
    public static <K, V> void reverse(Map<K, V> map) {
        ArrayListMap<K, V> map0 = new ArrayListMap<>(map);
        map.clear();
        for (int i = map0.size() - 1; i >= 0; i--)
            map.put(map0.getKey(i), map0.getValue(i));
    }
}