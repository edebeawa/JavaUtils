package top.edebe.util.jni;

import top.edebe.util.io.ClassResourceContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
class JavaUtilsNative {
    private static final ClassResourceContext CONTEXT = new ClassResourceContext(JavaUtilsNative.class);

    static {
        final File file;
        try {
            file = CONTEXT.getResourceAsTempFile("/lib/libJavaUtils.dll", "libEdebeUtils_", ".dll");
            System.load(file.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load native method", e);
        }
    }
}