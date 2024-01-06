package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;
import pers.edebe.util.base.ArrayUtils;

import java.util.*;
import java.util.function.Consumer;

public class ImmutableList<E> extends AbstractUnmodifiableList<E> {
    private final E[] elements;

    @SafeVarargs
    public ImmutableList(E... elements) {
        this.elements = elements;
    }

    @SuppressWarnings("unchecked")
    public ImmutableList(Collection<? extends E> c) {
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

    @Override
    public E get(int index) {
        return elements[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) if (elements[i].equals(o)) return i;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) if (elements[i].equals(o)) return i;
        return -1;
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ImmutableListIterator<>(index, elements);
    }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ImmutableList<>(new ArrayList<>(Arrays.asList(elements).subList(fromIndex, toIndex)));
    }
}
