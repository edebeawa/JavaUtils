package top.edebe.util.collect;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ImmutableListIterator<E> extends AbstractUnmodifiableListIterator<E> {
    private final E[] elements;
    private int index;

    @SafeVarargs
    public ImmutableListIterator(int index, E... elements) {
        this.elements = elements;
        this.index = index;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.elements.length;
    }

    @Override
    public E next() {
        final int i = this.index;
        if (i >= this.elements.length)
            throw new NoSuchElementException();
        return this.elements[this.index++];
    }

    @Override
    public boolean hasPrevious() {
        return this.index > 0;
    }

    @Override
    public E previous() {
        final int i = this.index - 1;
        if (i < 0)
            throw new NoSuchElementException();
        return this.elements[this.index = i];
    }

    @Override
    public int nextIndex() {
        return this.index + 1;
    }

    @Override
    public int previousIndex() {
        return this.index - 1;
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        super.forEachRemaining(action);
    }
}