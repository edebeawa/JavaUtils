package top.edebe.util.loader;

import top.edebe.util.wrapper.ClassWrapper;
import org.jetbrains.annotations.NotNull;
import sun.misc.Launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InternalClassLoaders {
    public static final ClassLoader EXT_LOADER;
    public static final ClassLoader APP_LOADER;

    static {
        try {
            final Method method = Class.forName("sun.misc.Launcher$ExtClassLoader").getDeclaredMethod("getExtClassLoader");
            method.setAccessible(true);
            EXT_LOADER = (ClassLoader) method.invoke(null);
            APP_LOADER = Launcher.getLauncher().getClassLoader();
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