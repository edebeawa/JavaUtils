package pers.edebe.util.jni;

import pers.edebe.util.io.ClassResourceContext;

import java.io.IOException;

class EdebeUtilsNative {
    private static final ClassResourceContext CONTEXT = new ClassResourceContext(EdebeUtilsNative.class);

    static {
        try {
            System.load(CONTEXT.getResourceAsTempFile("/lib/libEdebeUtils.dll", "temp_", ".dll").getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load native method", e);
        }
        initialize();
    }

    private static native void initialize();
}