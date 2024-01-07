package pers.edebe.util.wrapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractWrapper<T> {
    private final T object;

    @Override
    public String toString() {
        return object.toString();
    }
}