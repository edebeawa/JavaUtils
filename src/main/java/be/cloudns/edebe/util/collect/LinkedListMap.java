package be.cloudns.edebe.util.collect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class LinkedListMap<K, V> extends AbstractListMap<K, V> {
    private final List<K> keyList = new LinkedList<>();
    private final List<V> valueList = new LinkedList<>();

    public LinkedListMap(Map<? extends K, ? extends V> m) {
        this.putAll(m);
    }
}