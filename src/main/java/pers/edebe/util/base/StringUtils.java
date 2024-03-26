package pers.edebe.util.base;

import java.util.*;

public class StringUtils {
    public static final String NULL = String.valueOf((Object) null);

    public static String toLowerCase(String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    public static String toUpperCase(String string) {
        return string.toUpperCase(Locale.ROOT);
    }

    public static String toFirstLetterUpperCase(String string) {
        return toUpperCase(string.substring(0, 1)) + string.substring(1);
    }

    public static String toHumpForUnderline(String string) {
        List<String> list = new ArrayList<>(List.of(string.split("")));
        Set<Integer> set = new HashSet<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String cursor = list.get(i);
            int next = i + 1;
            if (cursor.equals("_") && next != size) {
                list.set(i, "");
                list.set(next, toUpperCase(list.get(next)));
                set.add(next);
            } else if (!cursor.equals("") && !set.contains(i)) {
                list.set(i, toLowerCase(cursor));
            }
        }
        StringBuilder builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public static String format(String pattern, Object... objects) {
        final int length = pattern.length();
        StringBuilder builder = new StringBuilder();
        int start = 0;
        for (Object object : objects) {
            int index = pattern.indexOf("{}", start);
            if (index == -1) {
                if (start == 0)
                    return pattern;
                builder.append(pattern, start, length);
                return builder.toString();
            } else {
                builder.append(pattern, start, index);
                if (ArrayUtils.isArray(object))
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