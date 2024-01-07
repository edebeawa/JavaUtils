package pers.edebe.util.collect;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImmutableEntry<K, V> extends AbstractUnmodifiableEntry<K, V> {
    private final K key;
    private final V value;
}