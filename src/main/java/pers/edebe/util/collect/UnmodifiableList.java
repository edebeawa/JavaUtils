package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public class UnmodifiableList<E> extends AbstractUnmodifiableList<E> {
    private final List<E> list;

    @SuppressWarnings("unchecked")
    public UnmodifiableList(List<? extends E> l) {
        list = (List<E>) l;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<>(list.iterator());
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        list.forEach(action);
    }

    @NotNull
    @Override
    @SuppressWarnings("NullableProblems")
    public Object[] toArray() {
        return list.toArray();
    }

    @NotNull
    @Override
    @SuppressWarnings({"NullableProblems", "SuspiciousToArrayCall"})
    public <T> T[] toArray(@NotNull T[] a) {
        return list.toArray(a);
    }

    @Override
    @SuppressWarnings("SlowListContainsAll")
    public boolean containsAll(@NotNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return new UnmodifiableListIterator<>(list.listIterator());
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new UnmodifiableListIterator<>(list.listIterator(index));
    }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new UnmodifiableList<>(list.subList(fromIndex, toIndex));
    }
}