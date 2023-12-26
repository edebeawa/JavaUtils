package pers.edebe.util.base;

import java.util.Locale;

public class StringUtils {
    public static String toLowerCase(String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    public static String toUpperCase(String string) {
        return string.toUpperCase(Locale.ROOT);
    }
}