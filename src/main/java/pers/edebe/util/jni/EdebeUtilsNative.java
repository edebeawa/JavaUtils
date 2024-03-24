package pers.edebe.util.jni;

import pers.edebe.util.io.ClassResourceContext;
import pers.edebe.util.io.FileUtils;

import java.io.IOException;

class EdebeUtilsNative {
    private static final ClassResourceContext CONTEXT = new ClassResourceContext(EdebeUtilsNative.class);

    static {
        try {
            System.load(FileUtils.findFile(CONTEXT.getResource("/lib/libEdebeUtils.dll"), (dir, name) ->
                    dir.resolve(name.substring(0, name.lastIndexOf("."))).resolve("libEdebeUtils.dll").toFile()
            ).getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load native method", e);
        }
        initialize();
    }

    private static native void initialize();
}