package top.edebe.util.base;

import org.jetbrains.annotations.NotNull;

public enum OS {
    WINDOWS, LINUX, MACOSX, SOLARIS, UNKNOWN;

    public static @NotNull OS get() {
        final String name = System.getProperty("os.name");
        if (name != null) {
            if (name.contains("Windows"))
                return OS.WINDOWS;
            if (name.contains("Linux"))
                return OS.LINUX;
            if (name.contains("Solaris") || name.contains("SunOS"))
                return OS.SOLARIS;
            if (name.contains("OS X"))
                return OS.MACOSX;
        }
        return OS.UNKNOWN;
    }
}