package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import top.edebe.util.collect.ArrayListMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

@UtilityClass
public class CollectionUtils {
    public static <K, V> void reverse(final @NotNull Map<K, V> map) {
        final ArrayListMap<K, V> m = new ArrayListMap<>(map);
        map.clear();
        for (int i = m.size() - 1; i >= 0; i--)
            map.put(m.getKey(i), m.getValue(i));
    }

    public static <T> void add(final Collection<T> collection, final int count, final T value) {
        for (int i = 0; i < count; i++)
            collection.add(value);
    }
}