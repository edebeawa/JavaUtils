package pers.edebe.util.function;

@FunctionalInterface
public interface ThrowableBiFunction<A0, A1, R, T extends Throwable> {
    R apply(A0 argument0, A1 argument1) throws T;
}