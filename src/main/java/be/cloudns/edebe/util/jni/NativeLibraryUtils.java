package be.cloudns.edebe.util.jni;

import be.cloudns.edebe.util.wrapper.ClassWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import be.cloudns.edebe.util.wrapper.FieldWrapper;
import be.cloudns.edebe.util.wrapper.MethodWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("SpellCheckingInspection")
public class NativeLibraryUtils extends JavaUtilsNative {
    private static final Class<?> NATIVE_LIBRARIES_CLASS;
    private static final FieldWrapper<Map<String, Object>> NATIVE_LIBRARIES_LIBRARIES_FIELD;
    private static final MethodWrapper<?> NATIVE_LIBRARIES_LOAD_LIBRARY_METHOD;
    private static final FieldWrapper<?> CLASS_LOADER_LIBRARIES_FIELD;
    private static final MethodWrapper<?> NATIVE_LIBRARY_IMPL_UNLOADER_METHOD;

    static {
        try {
            NATIVE_LIBRARIES_CLASS = Class.forName("jdk.internal.loader.NativeLibraries");
            //noinspection unchecked
            NATIVE_LIBRARIES_LIBRARIES_FIELD = (FieldWrapper<Map<String, Object>>) ClassWrapper.wrap(NATIVE_LIBRARIES_CLASS)
                    .getDeclaredFieldNoRestrict("libraries")
                    .setAccessibleNoRestrict(true);
            NATIVE_LIBRARIES_LOAD_LIBRARY_METHOD = ClassWrapper.wrap(NATIVE_LIBRARIES_CLASS)
                    .getDeclaredMethodNoRestrictExactMatch("loadLibrary", Class.class, File.class)
                    .setAccessibleNoRestrict(true);
            CLASS_LOADER_LIBRARIES_FIELD = ClassWrapper.wrap(ClassLoader.class)
                    .getDeclaredFieldNoRestrict("libraries")
                    .setAccessibleNoRestrict(true);
            NATIVE_LIBRARY_IMPL_UNLOADER_METHOD = ClassWrapper.wrap(Class.forName("jdk.internal.loader.NativeLibraries$NativeLibraryImpl"))
                    .getDeclaredMethodNoRestrictExactMatch("unloader")
                    .setAccessibleNoRestrict(true);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static Object findLibrary(ClassLoader loader, String name) throws ReflectiveOperationException {
        return NATIVE_LIBRARIES_LIBRARIES_FIELD.get(CLASS_LOADER_LIBRARIES_FIELD.get(loader)).get(name);
    }

    @Nullable
    public static Object findLibrary(Class<?> clazz, File file) throws ReflectiveOperationException, IOException {
        return findLibrary(clazz.getClassLoader(), file.getCanonicalPath());
    }

    public static Object loadTempLibrary(Class<?> clazz, File file) throws ReflectiveOperationException {
        Object library = loadLibrary(clazz, file);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                unloadLibrary(library);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }));
        return library;
    }

    public static Object loadLibrary(Class<?> clazz, File file) throws ReflectiveOperationException {
        return NATIVE_LIBRARIES_LOAD_LIBRARY_METHOD.invoke(CLASS_LOADER_LIBRARIES_FIELD.get(clazz.getClassLoader()), clazz, file);
    }

    public static void unloadLibrary(Object library) throws ReflectiveOperationException {
        ((Runnable) NATIVE_LIBRARY_IMPL_UNLOADER_METHOD.invoke(library)).run();
    }
}