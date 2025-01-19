package top.edebe.util.jni;

import top.edebe.util.wrapper.ClassWrapper;
import top.edebe.util.wrapper.FieldWrapper;
import top.edebe.util.wrapper.MethodWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativeLibraryUtils extends JavaUtilsNative {
    private static final FieldWrapper<String> NATIVE_LIBRARY_NAME_FIELD;
    private static final FieldWrapper<Boolean> NATIVE_LIBRARY_IS_BUILTIN_FIELD;
    private static final MethodWrapper<?> NATIVE_LIBRARY_UNLOAD_METHOD;
    private static final FieldWrapper<Vector<Object>> CLASS_LOADER_NATIVE_LIBRARIES_FIELD;
    private static final MethodWrapper<?> CLASS_LOADER_LOAD_LIBRARY0_METHOD;

    static {
        try {
            final Class<?> nativeLibraryClass = Class.forName("java.lang.ClassLoader$NativeLibrary");
            NATIVE_LIBRARY_NAME_FIELD = ClassWrapper.wrap(nativeLibraryClass)
                    .getDeclaredFieldNoRestrict("name")
                    .setAccessibleNoRestrict(true)
                    .setType(String.class);
            NATIVE_LIBRARY_IS_BUILTIN_FIELD = ClassWrapper.wrap(nativeLibraryClass)
                    .getDeclaredFieldNoRestrict("isBuiltin")
                    .setAccessibleNoRestrict(true)
                    .setType(Boolean.class);
            NATIVE_LIBRARY_UNLOAD_METHOD = ClassWrapper.wrap(nativeLibraryClass)
                    .getDeclaredMethodNoRestrict("unload", String.class, boolean.class)
                    .setAccessibleNoRestrict(true);
            //noinspection unchecked
            CLASS_LOADER_NATIVE_LIBRARIES_FIELD = (FieldWrapper<Vector<Object>>) ClassWrapper.wrap(ClassLoader.class)
                    .getDeclaredFieldNoRestrict("nativeLibraries")
                    .setAccessibleNoRestrict(true);
            CLASS_LOADER_LOAD_LIBRARY0_METHOD = ClassWrapper.wrap(ClassLoader.class)
                    .getDeclaredMethodNoRestrict("loadLibrary0", Class.class, File.class)
                    .setAccessibleNoRestrict(true);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nullable Object findLibrary(final @NotNull ClassLoader loader, final @NotNull String name) throws IllegalAccessException {
        for (final Object library : CLASS_LOADER_NATIVE_LIBRARIES_FIELD.get(loader))
            if (name.equals(NATIVE_LIBRARY_NAME_FIELD.get(library)))
                return library;
        return null;
    }

    public static @Nullable Object findLibrary(final @NotNull Class<?> clazz, final @NotNull File file) throws IOException, IllegalAccessException {
        return NativeLibraryUtils.findLibrary(clazz.getClassLoader(), file.getCanonicalPath());
    }

    public static @Nullable Object loadTempLibrary(final @NotNull Class<?> clazz, final @NotNull File file) throws InvocationTargetException, IllegalAccessException, IOException {
        final Object library = NativeLibraryUtils.loadLibrary(clazz, file);
        if (library != null) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    NativeLibraryUtils.unloadLibrary(library);
                }
                catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        return library;
    }

    public static @Nullable Object loadLibrary(final @NotNull Class<?> clazz, final @NotNull File file) throws IllegalAccessException, InvocationTargetException, IOException {
        CLASS_LOADER_LOAD_LIBRARY0_METHOD.invoke(clazz, file);
        return NativeLibraryUtils.findLibrary(clazz, file);
    }

    public static void unloadLibrary(final @NotNull Object library) throws InvocationTargetException, IllegalAccessException {
        NATIVE_LIBRARY_UNLOAD_METHOD.invoke(library, NATIVE_LIBRARY_NAME_FIELD.get(library), NATIVE_LIBRARY_IS_BUILTIN_FIELD.get(library));
    }
}