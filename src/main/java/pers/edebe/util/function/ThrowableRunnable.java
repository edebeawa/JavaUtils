package pers.edebe.util.function;

public interface ThrowableRunnable<T extends Throwable> {
    void run() throws T;
}