package pers.edebe.util.collect;

import java.util.HashMap;
import java.util.Map;

public class BidirectionalHashMap<K, V> extends AbstractBidirectionalMap<K, V> {
    private final HashMap<K, V> map;
    private final MapConstructor<BidirectionalHashMap<V, K>, V, K> constructor;

    public BidirectionalHashMap(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
        constructor = new MapConstructor<>(initialCapacity, loadFactor);
    }

    public BidirectionalHashMap(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
        constructor = new MapConstructor<>(initialCapacity);
    }

    public BidirectionalHashMap() {
        map = new HashMap<>();
        constructor = new MapConstructor<>();
    }

    public BidirectionalHashMap(Map<? extends K, ? extends V> m) {
        map = new HashMap<>(m);
        Map<V, K> reverse = new HashMap<>();
        m.forEach((key, value) -> reverse.put(value, key));
        constructor = new MapConstructor<>(reverse);
    }

    @Override
    protected HashMap<K, V> currentMap() {
        return map;
    }

    @Override
    protected BidirectionalHashMap<V, K> newReverseMap() {
        return constructor.newInstance(BidirectionalHashMap::new, BidirectionalHashMap::new, BidirectionalHashMap::new, BidirectionalHashMap::new);
    }
}