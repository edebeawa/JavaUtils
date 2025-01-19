package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@UtilityClass
public class StringUtils {
    public static final String NULL = String.valueOf((Object) null);

    public static @NotNull String toLowerCase(final @NotNull String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    public static @NotNull String toUpperCase(final @NotNull String string) {
        return string.toUpperCase(Locale.ROOT);
    }

    public static @NotNull String toFirstLetterUpperCase(final @NotNull String string) {
        return StringUtils.toUpperCase(string.substring(0, 1)) + string.substring(1);
    }

    public static @NotNull String toHumpForUnderline(final @NotNull String string) {
        final List<String> list = new ArrayList<>(List.of(string.split("")));
        final Set<Integer> set = new HashSet<>();
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            final String cursor = list.get(i);
            final int next = i + 1;
            if (cursor.equals("_") && next != size) {
                list.set(i, "");
                list.set(next, StringUtils.toUpperCase(list.get(next)));
                set.add(next);
            }
            else if (!cursor.equals("") && !set.contains(i)) {
                list.set(i, StringUtils.toLowerCase(cursor));
            }
        }
        final StringBuilder builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public static @NotNull String format(final @NotNull String pattern, final @Nullable Object @NotNull ... objects) {
        final int length = pattern.length();
        final StringBuilder builder = new StringBuilder(length < Integer.MAX_VALUE - 64 ? length + 64 : Integer.MAX_VALUE);
        int start = 0;
        for (Object object : objects) {
            final int index = pattern.indexOf("{}", start);
            if (index == -1) {
                if (start == 0)
                    return pattern;
                builder.append(pattern, start, length);
                return builder.toString();
            } else {
                builder.append(pattern, start, index);
                if (object != null && ArrayUtils.isArray(object))
                    builder.append(ArrayUtils.toString(ArrayUtils.toArray(object)));
                else
                    builder.append(object);
                start = index + 2;
            }
        }
        builder.append(pattern, start, length);
        return builder.toString();
    }
}