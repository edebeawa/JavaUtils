package pers.edebe.util.base;

import pers.edebe.util.collect.ImmutableCollection;
import pers.edebe.util.collect.ImmutableList;

import java.util.*;

public class StringUtils {
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
        List<String> list = new ArrayList<>(new ImmutableCollection<>(string.split("")));
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
}