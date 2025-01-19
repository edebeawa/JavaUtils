package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class AbstractStringBuilderUtils {
    public static @NotNull StringBuilder clear(final @NotNull StringBuilder builder) {
        return builder.delete(0, builder.length());
    }

    public static @NotNull String toStringAndClear(final @NotNull StringBuilder builder) {
        final String string = builder.toString();
        clear(builder);
        return string;
    }

    public static @NotNull StringBuffer clear(final @NotNull StringBuffer buffer) {
        return buffer.delete(0, buffer.length());
    }

    public static @NotNull String toStringAndClear(final @NotNull StringBuffer buffer) {
        final String string = buffer.toString();
        clear(buffer);
        return string;
    }
}