package be.cloudns.edebe.util.function;

@FunctionalInterface
public interface ThrowableConsumer<A, T extends Throwable> {
    void accept(A argument) throws T;
}