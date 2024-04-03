package pers.edebe.util.collect;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BidirectionalLinkedListMap<K, V> extends AbstractBidirectionalListMap<K, V> {
    private final List<K> keyList;
    private final List<V> valueList;

    public BidirectionalLinkedListMap() {
        keyList = new LinkedList<>();
        valueList = new LinkedList<>();
    }

    public BidirectionalLinkedListMap(Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }

    public BidirectionalLinkedListMap(List<K> keyList, List<V> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
    }

    @Override
    protected AbstractBidirectionalListMap<V, K> newReverseMap(List<V> keyList, List<K> valueList) {
        return new BidirectionalLinkedListMap<>(keyList, valueList);
    }
}