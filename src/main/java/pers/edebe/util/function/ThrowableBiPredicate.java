package pers.edebe.util.function;

@FunctionalInterface
public interface ThrowableBiPredicate<A0, A1, T extends Throwable> {
    boolean test(A0 argument0, A1 argument1) throws T;
}