package be.cloudns.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V> {
    private AbstractBiMap<V, K> inverse = null;

    protected abstract Map<K, V> currentMap();

    protected abstract AbstractBiMap<V, K> newInverseMap();

    @Override
    public AbstractBiMap<V, K> inverse() {
        if (inverse == null) {
            inverse = newInverseMap();
            inverse.inverse = this;
        }
        return inverse;
    }

    protected Map<V, K> inverseMap() {
        return inverse().currentMap();
    }

    @Override
    public int size() {
        if (currentMap().size() == inverseMap().size())
            return currentMap().size();
        else
            throw new ConcurrentModificationException();
    }

    @Override
    public boolean isEmpty() {
        return currentMap().isEmpty() && inverseMap().isEmpty();
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsKey(Object key) {
        return currentMap().containsKey(key) && inverseMap().containsValue(key);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsValue(Object value) {
        return currentMap().containsValue(value) && inverseMap().containsKey(value);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public V get(Object key) {
        if (inverseMap().containsValue(key))
            return currentMap().get(key);
        else
            throw new ConcurrentModificationException();
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        inverseMap().put(value, key);
        return currentMap().put(key, value);
    }

    @Override
    public V remove(Object key) {
        V value = currentMap().remove(key);
        inverseMap().remove(value);
        return value;
    }

    @Override
    public void clear() {
        currentMap().clear();
        inverseMap().clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return currentMap().keySet();
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return currentMap().values();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return currentMap().entrySet();
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public V getOrDefault(Object key, V defaultValue) {
        if (inverseMap().containsValue(key))
            return currentMap().getOrDefault(key, defaultValue);
        else
            return defaultValue;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return currentMap().remove(key, value) && inverseMap().remove(value, key);
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        inverseMap().replace(value, key);
        return currentMap().replace(key, value);
    }
}