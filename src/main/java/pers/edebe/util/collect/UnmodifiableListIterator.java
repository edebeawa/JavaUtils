package pers.edebe.util.collect;

import java.util.ListIterator;
import java.util.function.Consumer;

public class UnmodifiableListIterator<E> extends AbstractUnmodifiableListIterator<E> {
    private final ListIterator<E> iterator;

    @SuppressWarnings("unchecked")
    public UnmodifiableListIterator(ListIterator<? extends E> i) {
        iterator = (ListIterator<E>) i;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public E previous() {
        return iterator.previous();
    }

    @Override
    public int nextIndex() {
        return iterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterator.previousIndex();
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        iterator.forEachRemaining(action);
    }
}