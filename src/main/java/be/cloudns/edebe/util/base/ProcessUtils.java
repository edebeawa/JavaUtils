package be.cloudns.edebe.util.base;

import lombok.experimental.UtilityClass;
import be.cloudns.edebe.util.collect.ImmutableList;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
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

    public static long getCurrentProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(name.substring(0, name.indexOf("@")));
    }
}