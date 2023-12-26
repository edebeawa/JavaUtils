package pers.edebe.util.base;

import java.io.File;

public final class SystemUtils {
    public static void load(File file) {
        System.load(file.getAbsolutePath());
    }
}