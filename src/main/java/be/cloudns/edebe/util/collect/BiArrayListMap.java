package be.cloudns.edebe.util.collect;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BiArrayListMap<K, V> extends AbstractBiListMap<K, V> {
    private final List<K> keyList;
    private final List<V> valueList;

    public BiArrayListMap() {
        keyList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    public BiArrayListMap(Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }

    private BiArrayListMap(List<K> keyList, List<V> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
    }

    @Override
    protected AbstractBiListMap<V, K> newInverseMap(List<V> keyList, List<K> valueList) {
        return new BiArrayListMap<>(keyList, valueList);
    }
}