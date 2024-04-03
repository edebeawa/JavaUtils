package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractBidirectionalMap<K, V> extends AbstractMap<K, V> implements BidirectionalMap<K, V> {
    private AbstractBidirectionalMap<V, K> reverse = null;

    protected abstract Map<K, V> currentMap();

    protected abstract AbstractBidirectionalMap<V, K> newReverseMap();

    @Override
    public AbstractBidirectionalMap<V, K> reverse() {
        if (reverse == null) {
            reverse = newReverseMap();
            reverse.reverse = this;
        }
        return reverse;
    }

    protected Map<V, K> reverseMap() {
        return reverse().currentMap();
    }

    @Override
    public int size() {
        if (currentMap().size() == reverseMap().size())
            return currentMap().size();
        else
            throw new ConcurrentModificationException();
    }

    @Override
    public boolean isEmpty() {
        return currentMap().isEmpty() && reverseMap().isEmpty();
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsKey(Object key) {
        return currentMap().containsKey(key) && reverseMap().containsValue(key);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsValue(Object value) {
        return currentMap().containsValue(value) && reverseMap().containsKey(value);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public V get(Object key) {
        if (reverseMap().containsValue(key))
            return currentMap().get(key);
        else
            throw new ConcurrentModificationException();
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        reverseMap().put(value, key);
        return currentMap().put(key, value);
    }

    @Override
    public V remove(Object key) {
        V value = currentMap().remove(key);
        reverseMap().remove(value);
        return value;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        currentMap().clear();
        reverseMap().clear();
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
        if (reverseMap().containsValue(key))
            return currentMap().getOrDefault(key, defaultValue);
        else
            return defaultValue;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return currentMap().remove(key, value) && reverseMap().remove(value, key);
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        reverseMap().replace(value, key);
        return currentMap().replace(key, value);
    }
}