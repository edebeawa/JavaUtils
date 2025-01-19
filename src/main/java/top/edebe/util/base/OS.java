package top.edebe.util.base;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum OS {
    WINDOWS, LINUX, MACINTOSH, SOLARIS, UNKNOWN;

    public static @NotNull OS get() {
        final String name = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (name.contains("win"))
            return WINDOWS;
        else if (name.contains("linux") || name.contains("unix"))
            return LINUX;
        else if (name.contains("mac") || name.contains("os x"))
            return MACINTOSH;
        else if (name.contains("solaris") || name.contains("sunos"))
            return SOLARIS;
        else
            return UNKNOWN;
    }
}