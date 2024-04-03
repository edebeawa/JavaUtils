package pers.edebe.util.collect;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class ImmutableEntry<K, V> extends AbstractUnmodifiableEntry<K, V> {
    private final K key;
    private final V value;

    @Override
    public boolean equals(Object object) {
        if (object instanceof ImmutableEntry) {
            ImmutableEntry<?, ?> entry = (ImmutableEntry<?, ?>) object;
            return getKey().equals(entry.getKey()) && getValue().equals(entry.getValue());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }
}