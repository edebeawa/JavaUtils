package be.cloudns.edebe.util.function;

@FunctionalInterface
public interface ThrowableBiConsumer<A0, A1, T extends Throwable> {
    void accept(A0 argument0, A1 argument1) throws T;
}