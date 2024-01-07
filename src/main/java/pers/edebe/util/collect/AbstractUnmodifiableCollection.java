package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.function.Predicate;

public abstract class AbstractUnmodifiableCollection<E> extends AbstractCollection<E> implements Collection<E> {
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
    public void clear() {
        throw uoe("clear");
    }

    protected UnsupportedOperationException uoe(String method) {
        return new UnsupportedOperationException(method);
    }
}