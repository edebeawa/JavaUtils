package be.cloudns.edebe.util.jni;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import be.cloudns.edebe.util.io.ClassResourceContext;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
class JavaUtilsNative {
    private static final ClassResourceContext CONTEXT = new ClassResourceContext(JavaUtilsNative.class);

    static {
        File file;
        try {
            file = CONTEXT.getResourceAsTempFile("/lib/libJavaUtils.dll", "libEdebeUtils_", ".dll");
            System.load(file.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load native method", e);
        }
        initialize();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                NativeLibraryUtils.unloadLibrary(NativeLibraryUtils.findLibrary(JavaUtilsNative.class, file));
            }
            catch (ReflectiveOperationException | IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static native void initialize();
}