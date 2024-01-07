package pers.edebe.util.collect;

import java.util.Iterator;

public class UnmodifiableIterator<E> extends AbstractUnmodifiableIterator<E> {
    private final Iterator<E> iterator;

    @SuppressWarnings("unchecked")
    public UnmodifiableIterator(Iterator<? extends E> i) {
        iterator = (Iterator<E>) i;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }
}