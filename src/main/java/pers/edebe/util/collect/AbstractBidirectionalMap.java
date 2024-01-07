package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractBidirectionalMap<K, V> extends AbstractMap<K, V> implements BidirectionalMap<K, V> {
    protected abstract Map<K, V> currentMap();

    @Override
    public abstract AbstractBidirectionalMap<V, K> reverse();

    protected Map<V, K> reverseMap() {
        return reverse().currentMap();
    }

    @Override
    public int size() {
        if (currentMap().size() == reverseMap().size()) {
            return currentMap().size();
        } else {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public boolean isEmpty() {
        return currentMap().isEmpty() && reverseMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return currentMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return currentMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return currentMap().get(key);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        currentMap().put(key, value);
        reverseMap().put(value, key);
        return value;
    }

    @Override
    public V remove(Object key) {
        V value = currentMap().remove(key);
        reverseMap().remove(value);
        return value;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
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
    public V getOrDefault(Object key, V defaultValue) {
        return currentMap().getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        currentMap().forEach(action);
        reverseMap().forEach((value, key) -> action.accept(key, value));
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw uoe();
    }

    @Nullable
    @Override
    public V putIfAbsent(K key, V value) {
        reverseMap().putIfAbsent(value, key);
        return currentMap().putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return currentMap().remove(key, value) && reverseMap().remove(value, key);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw uoe();
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        reverseMap().replace(value, key);
        return currentMap().replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        throw uoe();
    }

    @Override
    public V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw uoe();
    }

    @Override
    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw uoe();
    }

    @Override
    public V merge(K key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw uoe();
    }

    protected UnsupportedOperationException uoe() {
        return new UnsupportedOperationException();
    }
}