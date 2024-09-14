package be.cloudns.edebe.util.collect;

import java.util.Map;

public abstract class AbstractUnmodifiableEntry<K, V> implements Map.Entry<K, V> {
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("setValue");
    }
}