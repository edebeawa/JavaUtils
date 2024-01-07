package pers.edebe.util.base;

public enum OS {
    WINDOWS, MAC, LINUX, OTHER;

    public static OS getOS() {
        String os = System.getProperty("os.name");
        if (os.contains("Mac") || os.contains("OS X"))
            return MAC;
        if (os.contains("Linux"))
            return LINUX;
        if (os.contains("Windows"))
            return WINDOWS;
        return OTHER;
    }
}