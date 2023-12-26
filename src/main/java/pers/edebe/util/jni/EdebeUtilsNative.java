package pers.edebe.util.jni;

import pers.edebe.util.base.ClassUtils;
import pers.edebe.util.base.FileType;
import pers.edebe.util.base.SystemUtils;
import pers.edebe.util.io.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

class EdebeUtilsNative {
    static {
        boolean throwException = false;
        URL url = EdebeUtilsNative.class.getResource("/pers/edebe/util/native/libEdebeUtils.dll");
        if (url != null) {
            try {
                Path path = ClassUtils.getPath(url);
                File file;
                if (FileType.JAR.isThisFileType(path)) {
                    String filepath = path.toString();
                    path = Path.of(filepath.substring(0, filepath.lastIndexOf(File.separator)));
                    file = path.resolve("libEdebeUtils.dll").toFile();
                    Files.createDirectories(path);
                    if (!file.exists() && file.createNewFile()) {
                        try (InputStream inputStream = url.openStream(); FileOutputStream outputStream = new FileOutputStream(file)) {
                            outputStream.write(StreamUtils.toByteArray(inputStream));
                        }
                    }
                } else {
                    file = path.toFile();
                }
                SystemUtils.load(file);
            } catch (IOException e) {
                throwException = true;
            }
        } else {
            throwException = true;
        }
        if (throwException) {
            throw new RuntimeException("Unable to load native method");
        }
    }
}