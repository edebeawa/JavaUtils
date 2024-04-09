package pers.edebe.util.collect;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BiLinkedListMap<K, V> extends AbstractBiListMap<K, V> {
    private final List<K> keyList;
    private final List<V> valueList;

    public BiLinkedListMap() {
        keyList = new LinkedList<>();
        valueList = new LinkedList<>();
    }

    public BiLinkedListMap(Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }

    public BiLinkedListMap(List<K> keyList, List<V> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
    }

    @Override
    protected AbstractBiListMap<V, K> newInverseMap(List<V> keyList, List<K> valueList) {
        return new BiLinkedListMap<>(keyList, valueList);
    }
}