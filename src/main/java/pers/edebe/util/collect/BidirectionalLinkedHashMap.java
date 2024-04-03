package pers.edebe.util.collect;

import java.util.LinkedHashMap;
import java.util.Map;

public class BidirectionalLinkedHashMap<K, V> extends AbstractBidirectionalMap<K, V> {
    private final LinkedHashMap<K, V> map;
    private final MapConstructor<BidirectionalLinkedHashMap<V, K>, V, K> constructor;

    public BidirectionalLinkedHashMap(int initialCapacity, float loadFactor) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
        constructor = new MapConstructor<>(initialCapacity, loadFactor);
    }

    public BidirectionalLinkedHashMap(int initialCapacity) {
        map = new LinkedHashMap<>(initialCapacity);
        constructor = new MapConstructor<>(initialCapacity);
    }

    public BidirectionalLinkedHashMap() {
        map = new LinkedHashMap<>();
        constructor = new MapConstructor<>();
    }

    public BidirectionalLinkedHashMap(Map<? extends K, ? extends V> m) {
        map = new LinkedHashMap<>(m);
        Map<V, K> reverse = new LinkedHashMap<>();
        m.forEach((key, value) -> reverse.put(value, key));
        constructor = new MapConstructor<>(reverse);
    }

    @Override
    protected LinkedHashMap<K, V> currentMap() {
        return map;
    }

    @Override
    protected BidirectionalLinkedHashMap<V, K> newReverseMap() {
        return constructor.newInstance(BidirectionalLinkedHashMap::new, BidirectionalLinkedHashMap::new, BidirectionalLinkedHashMap::new, BidirectionalLinkedHashMap::new);
    }
}