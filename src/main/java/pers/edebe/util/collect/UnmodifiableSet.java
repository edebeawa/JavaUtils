package pers.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class UnmodifiableSet<E> extends AbstractUnmodifiableSet<E> {
    private final Set<E> set;

    @SuppressWarnings("unchecked")
    public UnmodifiableSet(Set<? extends E> s) {
        set = (Set<E>) s;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<>(set.iterator());
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        set.forEach(action);
    }

    @NotNull
    @Override
    @SuppressWarnings("NullableProblems")
    public Object[] toArray() {
        return set.toArray();
    }

    @NotNull
    @Override
    @SuppressWarnings({"NullableProblems", "SuspiciousToArrayCall"})
    public <T> T[] toArray(@NotNull T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return set.containsAll(c);
    }
}