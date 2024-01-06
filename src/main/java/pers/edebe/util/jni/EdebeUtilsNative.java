package pers.edebe.util.jni;

import pers.edebe.util.io.FileUtils;

import java.io.IOException;
import java.net.URL;

class EdebeUtilsNative {
    static {
        boolean throwException = false;
        URL url = EdebeUtilsNative.class.getResource("/lib/libEdebeUtils.dll");
        if (url != null) {
            try {
                System.load(FileUtils.findFile(url, (dir, name) ->
                        dir.resolve(name.substring(0, name.lastIndexOf("."))).resolve("libEdebeUtils.dll").toFile()
                ).getAbsolutePath());
            } catch (IOException e) {
                throwException = true;
            }
        } else {
            throwException = true;
        }
        if (throwException) {
            throw new RuntimeException("Unable to load native method");
        }
        initialize();
    }

    private static native void initialize();
}