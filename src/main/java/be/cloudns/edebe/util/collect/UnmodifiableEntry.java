package be.cloudns.edebe.util.collect;

import java.util.Map.Entry;

public class UnmodifiableEntry<K, V> extends AbstractUnmodifiableEntry<K, V> {
    private final Entry<K, V> entry;

    @SuppressWarnings("unchecked")
    public UnmodifiableEntry(Entry<? extends K, ? extends V> e) {
        entry = (Entry<K, V>) e;
    }

    @Override
    public K getKey() {
        return entry.getKey();
    }

    @Override
    public V getValue() {
        return entry.getValue();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof UnmodifiableEntry<?, ?> unmodifiable)
            return entry.equals(unmodifiable.entry);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return entry.hashCode();
    }
}