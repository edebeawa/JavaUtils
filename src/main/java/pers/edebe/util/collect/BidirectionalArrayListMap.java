package pers.edebe.util.collect;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BidirectionalArrayListMap<K, V> extends AbstractBidirectionalListMap<K, V> {
    private final List<K> keyList;
    private final List<V> valueList;

    public BidirectionalArrayListMap() {
        keyList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    public BidirectionalArrayListMap(Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }

    private BidirectionalArrayListMap(List<K> keyList, List<V> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
    }

    @Override
    protected AbstractBidirectionalListMap<V, K> newReverseMap(List<V> keyList, List<K> valueList) {
        return new BidirectionalArrayListMap<>(keyList, valueList);
    }
}