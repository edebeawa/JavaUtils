package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ImmutableMap<K, V> extends AbstractUnmodifiableMap<K, V> {
    private final K[] keys;
    private final V[] values;

    public ImmutableMap(K[] keys, V[] values) {
        this.keys = keys;
        this.values = values;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public ImmutableMap(Entry<? extends K, ? extends V>... entries) {
        int length = entries.length;
        K[] keys = (K[]) new Object[length];
        V[] values = (V[]) new Object[length];
        for (int i = 0; i < length; i++) {
            Entry<? extends K, ? extends V> entry = entries[i];
            keys[i] = entry.getKey();
            values[i] = entry.getValue();
        }
        this.keys = keys;
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public ImmutableMap(Map<? extends K, ? extends V> m) {
        this(m.entrySet().toArray(new Entry[0]));
    }

    @Override
    public int size() {
        if (keys.length == values.length) {
            return keys.length;
        } else {
            throw new IllegalStateException("The size is out of sync");
        }
    }

    protected int indexOf(Object[] objects, Object object) {
        for (int i = 0; i < size(); i++) if (objects[i].equals(object)) return i;
        return -1;
    }

    @Override
    public boolean containsKey(Object key) {
        return indexOf(keys, key) != -1;
    }

    @Override
    public boolean containsValue(Object value) {
        return indexOf(values, value) != -1;
    }

    @Override
    public V get(Object key) {
        int index = indexOf(keys, key);
        return index == -1 ? null : values[index];
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return new ImmutableSet<>(keys);
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return new ImmutableCollection<>(values);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public Set<Entry<K, V>> entrySet() {
        int length = size();
        Entry<K, V>[] entries = new Entry[length];
        for (int i = 0; i < length; i++) entries[i] = new ImmutableEntry<>(keys[i], values[i]);
        return new ImmutableSet<>(entries);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int i = 0; i < size(); i++) action.accept(keys[i], values[i]);
    }
}