package top.edebe.util.loader;

import top.edebe.util.wrapper.ClassWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class InternalClassLoaders {
    public static final ClassLoader BOOT_LOADER;
    public static final ClassLoader PLATFORM_LOADER;
    public static final ClassLoader APP_LOADER;

    static {
        try {
            final ClassWrapper<?> wrapper = ClassWrapper.wrap(Class.forName("jdk.internal.loader.ClassLoaders"));
            BOOT_LOADER = invoke(wrapper, "bootLoader");
            PLATFORM_LOADER = invoke(wrapper, "platformClassLoader");
            APP_LOADER = invoke(wrapper, "appClassLoader");
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClassLoader invoke(final ClassWrapper<?> wrapper, final @NotNull String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return wrapper.getDeclaredMethodNoRestrict(name)
                .setAccessibleNoRestrict(true)
                .setType(ClassLoader.class)
                .invokeStaticNoRestrict();
    }
}