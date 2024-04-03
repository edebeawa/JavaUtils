package pers.edebe.util.collect;

import org.jetbrains.annotations.Nullable;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.BiFunction;

public abstract class AbstractBidirectionalListMap<K, V> extends AbstractListMap<K, V> implements BidirectionalMap<K, V> {
    private AbstractBidirectionalListMap<V, K> reverse = null;

    protected abstract AbstractBidirectionalListMap<V, K> newReverseMap(List<V> keyList, List<K> valueList);

    @Override
    public AbstractBidirectionalListMap<V, K> reverse() {
        if (reverse == null) {
            reverse = newReverseMap(valueList(), keyList());
            reverse.reverse = this;
        }
        return reverse;
    }

    private V tryModify(K key, V value, BiFunction<Integer, Integer, V> function) {
        int keyIndex = keyList().indexOf(key);
        int valueIndex = valueList().indexOf(value);
        if (keyIndex == -1 && valueIndex == -1)
            return function.apply(keyIndex, valueIndex);
        else if (keyIndex != -1 && valueIndex != -1) {
            keyList().set(valueIndex, key);
            return valueList().set(keyIndex, value);
        }
        throw new ConcurrentModificationException();
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        return tryModify(key, value, (keyIndex, valueIndex) -> {
            keyList().add(key);
            valueList().add(value);
            return value;
        });
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        return tryModify(key, value, (keyIndex, valueIndex) -> null);
    }
}