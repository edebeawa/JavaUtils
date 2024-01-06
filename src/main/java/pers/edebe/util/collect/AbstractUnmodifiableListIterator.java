package pers.edebe.util.collect;

import java.util.ListIterator;

public abstract class AbstractUnmodifiableListIterator<E> implements ListIterator<E> {
    @Override
    public void remove() {
        throw uoe("remove");
    }

    @Override
    public void set(E e) {
        throw uoe("set");
    }

    @Override
    public void add(E e) {
        throw uoe("add");
    }

    protected UnsupportedOperationException uoe(String method) {
        return new UnsupportedOperationException(method);
    }
}