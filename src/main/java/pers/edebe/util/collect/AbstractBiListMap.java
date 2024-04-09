package pers.edebe.util.collect;

import org.jetbrains.annotations.Nullable;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.BiFunction;

public abstract class AbstractBiListMap<K, V> extends AbstractListMap<K, V> implements BiMap<K, V> {
    private AbstractBiListMap<V, K> inverse = null;

    protected abstract AbstractBiListMap<V, K> newInverseMap(List<V> keyList, List<K> valueList);

    @Override
    public AbstractBiListMap<V, K> inverse() {
        if (inverse == null) {
            inverse = newInverseMap(valueList(), keyList());
            inverse.inverse = this;
        }
        return inverse;
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