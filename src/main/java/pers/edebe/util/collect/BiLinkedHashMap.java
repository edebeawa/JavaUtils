package pers.edebe.util.collect;

import java.util.LinkedHashMap;
import java.util.Map;

public class BiLinkedHashMap<K, V> extends AbstractBiMap<K, V> {
    private final LinkedHashMap<K, V> map;
    private final MapConstructor<BiLinkedHashMap<V, K>, V, K> constructor;

    public BiLinkedHashMap(int initialCapacity, float loadFactor) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
        constructor = new MapConstructor<>(initialCapacity, loadFactor);
    }

    public BiLinkedHashMap(int initialCapacity) {
        map = new LinkedHashMap<>(initialCapacity);
        constructor = new MapConstructor<>(initialCapacity);
    }

    public BiLinkedHashMap() {
        map = new LinkedHashMap<>();
        constructor = new MapConstructor<>();
    }

    public BiLinkedHashMap(Map<? extends K, ? extends V> m) {
        map = new LinkedHashMap<>(m);
        Map<V, K> inverse = new LinkedHashMap<>();
        m.forEach((key, value) -> inverse.put(value, key));
        constructor = new MapConstructor<>(inverse);
    }

    @Override
    protected LinkedHashMap<K, V> currentMap() {
        return map;
    }

    @Override
    protected BiLinkedHashMap<V, K> newInverseMap() {
        return constructor.newInstance(BiLinkedHashMap::new, BiLinkedHashMap::new, BiLinkedHashMap::new, BiLinkedHashMap::new);
    }
}