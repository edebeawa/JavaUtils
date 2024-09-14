package be.cloudns.edebe.util.base;

import java.util.Locale;

public enum OS {
    WINDOWS, LINUX, MACINTOSH, SOLARIS, UNKNOWN;

    public static OS get() {
        String name = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (name.contains("win"))
            return WINDOWS;
        if (name.contains("linux") || name.contains("unix"))
            return LINUX;
        if (name.contains("mac") || name.contains("os x"))
            return MACINTOSH;
        if (name.contains("solaris") || name.contains("sunos"))
            return SOLARIS;
        return UNKNOWN;
    }
}