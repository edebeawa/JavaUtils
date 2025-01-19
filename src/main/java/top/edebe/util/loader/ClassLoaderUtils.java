package top.edebe.util.loader;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ClassLoaderUtils {
    public static @NotNull ClassLoader requireNonNull(final @Nullable ClassLoader loader) {
        return loader == null ? InternalClassLoaders.BOOT_LOADER : loader;
    }
}