package be.cloudns.edebe.util.function;

@FunctionalInterface
public interface ThrowableFunction<A, R, T extends Throwable> {
    R apply(A argument) throws T;
}