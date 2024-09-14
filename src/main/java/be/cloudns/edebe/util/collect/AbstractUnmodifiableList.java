package be.cloudns.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class AbstractUnmodifiableList<E> extends AbstractList<E> implements List<E> {
    @Override
    public boolean add(E e) {
        throw uoe("add");
    }

    @Override
    public boolean remove(Object o) {
        throw uoe("remove");
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        throw uoe("addAll");
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        throw uoe("addAll");
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw uoe("removeAll");
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw uoe("removeIf");
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw uoe("retainAll");
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw uoe("replaceAll");
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw uoe("sort");
    }

    @Override
    public void clear() {
        throw uoe("clear");
    }

    @Override
    public E set(int index, E element) {
        throw uoe("set");
    }

    @Override
    public void add(int index, E element) {
        throw uoe("add");
    }

    @Override
    public E remove(int index) {
        throw uoe("remove");
    }

    protected UnsupportedOperationException uoe(String method) {
        return new UnsupportedOperationException(method);
    }
}