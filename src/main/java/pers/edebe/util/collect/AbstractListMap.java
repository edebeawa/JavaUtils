package pers.edebe.util.collect;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    @SuppressWarnings("SuspiciousMethodCalls")
    public int keyIndexOf(Object key) {
        return keyList().indexOf(key);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public int valueIndexOf(Object value) {
        return valueList().indexOf(value);
    }

    public K getKey(int index) {
        return keyList().get(index);
    }

    public V getValue(int index) {
        return valueList().get(index);
    }

    @Override
    public V get(Object key) {
        int index = keyIndexOf(key);
        if (index == -1)
            return null;
        else
            return getValue(index);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        int index = keyIndexOf(key);
        if (index == -1) {
            keyList().add(key);
            valueList().add(value);
        } else
            replaceValue(index, value);
        return value;
    }

    public V remove(int index) {
        keyList().remove(index);
        return valueList().remove(index);
    }

    @Override
    public V remove(Object key) {
        int index = keyIndexOf(key);
        if (index == -1)
            return null;
        else
            return remove(index);
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

    public K replaceKey(int index, K key) {
        return keyList().set(index, key);
    }

    public V replaceValue(int index, V value) {
        return valueList().set(index, value);
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        int index = keyIndexOf(key);
        if (index == -1)
            return null;
        else
            return replaceValue(index, value);
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