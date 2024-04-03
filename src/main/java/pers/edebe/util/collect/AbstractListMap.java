package pers.edebe.util.collect;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractListMap<K, V> extends AbstractMap<K, V> {
    protected abstract List<K> keyList();

    protected abstract List<V> valueList();

    @Override
    public int size() {
        if (keyList().size() == valueList().size())
            return keyList().size();
        else
            throw new ConcurrentModificationException();
    }

    @Override
    public boolean isEmpty() {
        return keyList().isEmpty() && valueList().isEmpty();
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsKey(Object key) {
        return keyList().contains(key);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsValue(Object value) {
        return valueList().contains(value);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public V get(Object key) {
        int index = keyList().indexOf(key);
        if (index == -1)
            return null;
        else
            return valueList().get(index);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private V tryModify(Object key, Function<Integer, V> function0, Function<Integer, V> function1) {
        int index = keyList().indexOf(key);
        if (index == -1)
            return function0.apply(index);
        else
            return function1.apply(index);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        return tryModify(key, (index) -> {
            keyList().add(key);
            valueList().add(value);
            return value;
        }, (index) -> {
            valueList().set(index, value);
            return value;
        });
    }

    @Override
    public V remove(Object key) {
        return tryModify(key, (index) -> null, (index) -> {
            keyList().remove(index.intValue());
            return valueList().remove(index.intValue());
        });
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        keyList().clear();
        valueList().clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return new LinkedHashSet<>(keyList());
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return new ArrayList<>(valueList());
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new LinkedHashSet<>();
        for (int i = 0; i < size(); i++)
            set.add(new Element<>(this, i));
        return set;
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        return tryModify(key, (index) -> null, (index) -> valueList().set(index, value));
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Element<K, V> implements Entry<K, V> {
        private final AbstractListMap<K, V> map;
        private final int index;

        @Override
        public K getKey() {
            return map.keyList().get(index);
        }

        @Override
        public V getValue() {
            return map.valueList().get(index);
        }

        @Override
        public V setValue(V value) {
            return map.valueList().set(index, value);
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Element)
                return index == ((Element<?, ?>) object).index;
            else
                return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue()) ^ Objects.hashCode(index);
        }
    }
}