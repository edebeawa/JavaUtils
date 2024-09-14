package be.cloudns.edebe.util.function;

@FunctionalInterface
public interface ThrowablePredicate<A, T extends Throwable> {
    boolean test(A argument) throws T;
}