package top.edebe.util.wrapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractWrapper<T> {
    private final T object;

    @Override
    public @NotNull String toString() {
        return String.valueOf(this.object);
    }
}