package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import pers.edebe.util.base.ArrayUtils;

import java.util.*;
import java.util.function.Consumer;

public class ImmutableSet<E> extends AbstractUnmodifiableSet<E> {
    private final E[] elements;

    @SafeVarargs
    public ImmutableSet(E... elements) {
        this.elements = elements;
    }

    @SuppressWarnings("unchecked")
    public ImmutableSet(Collection<? extends E> c) {
        Object[] objects = c.toArray();
        int length = objects.length;
        E[] elements = (E[]) new Object[length];
        for (int i = 0; i < length; i++) elements[i] = (E) objects[i];
        this.elements = elements;
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean contains(Object o) {
        for (E element : elements) if (element.equals(o)) return true;
        return false;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new ImmutableIterator<>(elements);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (E element : elements) action.accept(element);
    }

    @NotNull
    @Override
    @SuppressWarnings("NullableProblems")
    public Object[] toArray() {
        return elements;
    }

    @NotNull
    @Override
    @SuppressWarnings({"unchecked", "NullableProblems"})
    public <T> T[] toArray(@NotNull T[] a) {
        return (T[]) ArrayUtils.cast(elements, a.getClass().getComponentType(), size());
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return Arrays.equals(elements, c.toArray());
    }
}