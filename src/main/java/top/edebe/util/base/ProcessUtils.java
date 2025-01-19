package top.edebe.util.base;

import top.edebe.util.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
    public static @NotNull ProcessBuilder fileExecuteBuilder(final @NotNull File file, final @NotNull List<String> command) {
        final List<String> list = new ArrayList<>();
        list.add(file.getAbsolutePath());
        list.addAll(command);
        return new ProcessBuilder(list).directory(file.getParentFile());
    }

    public static @NotNull ProcessBuilder fileExecuteBuilder(final @NotNull File file, final @NotNull String @NotNull ... command) {
        return ProcessUtils.fileExecuteBuilder(file, new ImmutableList<>(command));
    }
}