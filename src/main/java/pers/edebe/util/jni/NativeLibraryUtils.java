package pers.edebe.util.jni;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import pers.edebe.util.wrapper.ClassWrapper;
import pers.edebe.util.wrapper.FieldWrapper;
import pers.edebe.util.wrapper.MethodWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativeLibraryUtils extends EdebeUtilsNative {
    private static final Class<?> NATIVE_LIBRARY_CLASS;
    private static final FieldWrapper<String> NATIVE_LIBRARY_NAME_FIELD;
    private static final FieldWrapper<Boolean> NATIVE_LIBRARY_IS_BUILTIN_FIELD;
    private static final MethodWrapper<?> NATIVE_LIBRARY_UNLOAD_METHOD;
    private static final FieldWrapper<Vector<Object>> CLASS_LOADER_NATIVE_LIBRARIES_FIELD;
    private static final MethodWrapper<?> CLASS_LOADER_LOAD_LIBRARY0_METHOD;

    static {
        try {
            NATIVE_LIBRARY_CLASS = Class.forName("java.lang.ClassLoader$NativeLibrary");
            NATIVE_LIBRARY_NAME_FIELD = ClassWrapper.wrap(NATIVE_LIBRARY_CLASS)
                    .getDeclaredFieldNoRestrict("name")
                    .setAccessibleNoRestrict(true)
                    .setType(String.class);
            NATIVE_LIBRARY_IS_BUILTIN_FIELD = ClassWrapper.wrap(NATIVE_LIBRARY_CLASS)
                    .getDeclaredFieldNoRestrict("isBuiltin")
                    .setAccessibleNoRestrict(true)
                    .setType(Boolean.class);
            NATIVE_LIBRARY_UNLOAD_METHOD = ClassWrapper.wrap(NATIVE_LIBRARY_CLASS)
                    .getDeclaredMethodNoRestrictExactMatch("unload", String.class, boolean.class)
                    .setAccessibleNoRestrict(true);
            //noinspection unchecked
            CLASS_LOADER_NATIVE_LIBRARIES_FIELD = (FieldWrapper<Vector<Object>>) ClassWrapper.wrap(ClassLoader.class)
                    .getDeclaredFieldNoRestrict("nativeLibraries")
                    .setAccessibleNoRestrict(true);
            CLASS_LOADER_LOAD_LIBRARY0_METHOD = ClassWrapper.wrap(ClassLoader.class)
                    .getDeclaredMethodNoRestrictExactMatch("loadLibrary0", Class.class, File.class)
                    .setAccessibleNoRestrict(true);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static Object findLibrary(ClassLoader loader, String name) throws ReflectiveOperationException {
        for (Object library : CLASS_LOADER_NATIVE_LIBRARIES_FIELD.get(loader))
            if (name.equals(NATIVE_LIBRARY_NAME_FIELD.get(library)))
                return library;
        return null;
    }

    @Nullable
    public static Object findLibrary(Class<?> clazz, File file) throws ReflectiveOperationException, IOException {
        return findLibrary(clazz.getClassLoader(), file.getCanonicalPath());
    }

    public static Object loadLibrary(Class<?> clazz, File file) throws ReflectiveOperationException, IOException {
        CLASS_LOADER_LOAD_LIBRARY0_METHOD.invoke(clazz, file);
        return findLibrary(clazz, file);
    }

    public static void unloadLibrary(Object library) throws ReflectiveOperationException {
        NATIVE_LIBRARY_UNLOAD_METHOD.invoke(library, NATIVE_LIBRARY_NAME_FIELD.get(library), NATIVE_LIBRARY_IS_BUILTIN_FIELD.get(library));
    }
}