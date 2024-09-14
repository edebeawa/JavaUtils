package be.cloudns.edebe.util.collect;

import java.util.HashMap;
import java.util.Map;

public class BiHashMap<K, V> extends AbstractBiMap<K, V> {
    private final HashMap<K, V> map;
    private final MapConstructor<BiHashMap<V, K>, V, K> constructor;

    public BiHashMap(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
        constructor = new MapConstructor<>(initialCapacity, loadFactor);
    }

    public BiHashMap(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
        constructor = new MapConstructor<>(initialCapacity);
    }

    public BiHashMap() {
        map = new HashMap<>();
        constructor = new MapConstructor<>();
    }

    public BiHashMap(Map<? extends K, ? extends V> m) {
        map = new HashMap<>(m);
        Map<V, K> inverse = new HashMap<>();
        m.forEach((key, value) -> inverse.put(value, key));
        constructor = new MapConstructor<>(inverse);
    }

    @Override
    protected HashMap<K, V> currentMap() {
        return map;
    }

    @Override
    protected BiHashMap<V, K> newInverseMap() {
        return constructor.newInstance(BiHashMap::new, BiHashMap::new, BiHashMap::new, BiHashMap::new);
    }
}