package pers.edebe.util.base;

public enum OS {
    WINDOWS, LINUX, MACINTOSH, UNKNOWN;

    public static OS get() {
        String name = System.getProperty("os.name");
        if (name.contains("Windows"))
            return WINDOWS;
        if (name.contains("Linux"))
            return LINUX;
        if (name.contains("Mac") || name.contains("OS X"))
            return MACINTOSH;
        return UNKNOWN;
    }
}