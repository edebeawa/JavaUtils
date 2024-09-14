package be.cloudns.edebe.util.collect;

import java.util.function.Consumer;

public class ImmutableListIterator<E> extends AbstractUnmodifiableListIterator<E> {
    private final E[] elements;
    private int index;

    @SafeVarargs
    public ImmutableListIterator(int index, E... elements) {
        this.elements = elements;
        this.index = index - 1;
    }

    @Override
    public boolean hasNext() {
        return index < elements.length - 1;
    }

    @Override
    public E next() {
        index++;
        return elements[index];
    }

    @Override
    public boolean hasPrevious() {
        return index > -1;
    }

    @Override
    public E previous() {
        E element = elements[index];
        index--;
        return element;
    }

    @Override
    public int nextIndex() {
        return index + 1;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        super.forEachRemaining(action);
    }
}