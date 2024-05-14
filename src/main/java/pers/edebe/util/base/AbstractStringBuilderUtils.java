package pers.edebe.util.base;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AbstractStringBuilderUtils {
    public static StringBuilder clear(StringBuilder builder) {
        return builder.delete(0, builder.length());
    }

    public static String toStringAndClear(StringBuilder builder) {
        String string = builder.toString();
        clear(builder);
        return string;
    }

    public static StringBuffer clear(StringBuffer buffer) {
        return buffer.delete(0, buffer.length());
    }

    public static String toStringAndClear(StringBuffer buffer) {
        String string = buffer.toString();
        clear(buffer);
        return string;
    }
}