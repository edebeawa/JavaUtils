package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractUnmodifiableMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    @Nullable
    @Override
    public V put(K key, V value) {
        throw uoe("put");
    }

    @Override
    public V remove(Object key) {
        throw uoe("remove");
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        throw uoe("putAll");
    }

    @Override
    public void clear() {
        throw uoe("clear");
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw uoe("replaceAll");
    }

    @Nullable
    @Override
    public V putIfAbsent(K key, V value) {
        throw uoe("putIfAbsent");
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw uoe("remove");
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw uoe("replace");
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        throw uoe("replace");
    }

    @Override
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        throw uoe("computeIfAbsent");
    }

    @Override
    public V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw uoe("computeIfPresent");
    }

    @Override
    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw uoe("compute");
    }

    @Override
    public V merge(K key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw uoe("merge");
    }

    protected UnsupportedOperationException uoe(String method) {
        return new UnsupportedOperationException(method);
    }
}