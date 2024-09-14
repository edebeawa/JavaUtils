package be.cloudns.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class UnmodifiableMap<K, V> extends AbstractUnmodifiableMap<K, V> {
    private final Map<K, V> map;

    @SuppressWarnings("unchecked")
    public UnmodifiableMap(Map<? extends K, ? extends V> m) {
        map = (Map<K, V>) m;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return new UnmodifiableSet<>(map.keySet());
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return new UnmodifiableCollection<>(map.values());
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new UnmodifiableSet<>(map.entrySet());
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }
}