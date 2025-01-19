package top.edebe.util.function;

@FunctionalInterface
public interface ThrowableSupplier<A, T extends Throwable> {
    A get() throws T;
}