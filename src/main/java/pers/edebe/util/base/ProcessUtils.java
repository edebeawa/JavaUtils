package pers.edebe.util.base;

import pers.edebe.util.collect.ImmutableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
    public static ProcessBuilder fileExecuteBuilder(File file, List<String> command) {
        List<String> list = new ArrayList<>();
        list.add(file.getAbsolutePath());
        list.addAll(command);
        return new ProcessBuilder(list).directory(file.getParentFile());
    }

    public static ProcessBuilder fileExecuteBuilder(File file, String... command) {
        return fileExecuteBuilder(file, new ImmutableList<>(command));
    }
}