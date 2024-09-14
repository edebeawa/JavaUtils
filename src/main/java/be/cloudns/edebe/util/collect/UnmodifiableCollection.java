package be.cloudns.edebe.util.collect;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class UnmodifiableCollection<E> extends AbstractUnmodifiableCollection<E> {
    private final Collection<E> collection;

    @SuppressWarnings("unchecked")
    public UnmodifiableCollection(Collection<? extends E> c) {
        collection = (Collection<E>) c;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<>(collection.iterator());
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        collection.forEach(action);
    }

    @NotNull
    @Override
    @SuppressWarnings("NullableProblems")
    public Object[] toArray() {
        return collection.toArray();
    }

    @NotNull
    @Override
    @SuppressWarnings({"NullableProblems", "SuspiciousToArrayCall"})
    public <T> T[] toArray(@NotNull T[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return collection.containsAll(c);
    }
}