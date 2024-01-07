package pers.edebe.util.collect;

import java.util.Map;

public interface BidirectionalMap<K, V> extends Map<K, V> {
    BidirectionalMap<V, K> reverse();
}