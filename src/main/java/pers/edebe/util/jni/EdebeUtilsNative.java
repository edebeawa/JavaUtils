package pers.edebe.util.jni;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pers.edebe.util.io.ClassResourceContext;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
class EdebeUtilsNative {
    private static final ClassResourceContext CONTEXT = new ClassResourceContext(EdebeUtilsNative.class);

    static {
        File file;
        try {
            file = CONTEXT.getResourceAsTempFile("/lib/libEdebeUtils.dll", "libEdebeUtils_", ".dll");
            System.load(file.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load native method", e);
        }
        initialize();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                NativeLibraryUtils.unloadLibrary(NativeLibraryUtils.findLibrary(EdebeUtilsNative.class, file));
            }
            catch (ReflectiveOperationException | IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static native void initialize();
}