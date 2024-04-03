package pers.edebe.util.collect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class ArrayListMap<K, V> extends AbstractListMap<K, V> {
    private final List<K> keyList = new ArrayList<>();
    private final List<V> valueList = new ArrayList<>();

    public ArrayListMap(Map<? extends K, ? extends V> m) {
        this.putAll(m);
    }
}