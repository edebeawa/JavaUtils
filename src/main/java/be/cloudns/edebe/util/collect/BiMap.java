package be.cloudns.edebe.util.collect;

import java.util.Map;

public interface BiMap<K, V> extends Map<K, V> {
    BiMap<V, K> inverse();
}