package pers.edebe.util.function;

import java.io.IOException;

public interface IOSupplier<T> {
    T get() throws IOException;
}