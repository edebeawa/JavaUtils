package pers.edebe.util.function;

import java.io.IOException;

public interface BiIOConsumer<T, U> {
    void accept(T t, U u) throws IOException;
}