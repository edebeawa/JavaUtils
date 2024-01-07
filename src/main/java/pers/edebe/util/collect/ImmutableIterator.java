package pers.edebe.util.collect;

public class ImmutableIterator<E> extends AbstractUnmodifiableIterator<E> {
    private final E[] elements;
    private int index = -1;

    @SafeVarargs
    public ImmutableIterator(E... elements) {
        this.elements = elements;
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
}