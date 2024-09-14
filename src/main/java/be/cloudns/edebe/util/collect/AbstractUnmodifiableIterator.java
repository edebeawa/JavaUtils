package be.cloudns.edebe.util.collect;

import java.util.Iterator;

public abstract class AbstractUnmodifiableIterator<E> implements Iterator<E> {
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}