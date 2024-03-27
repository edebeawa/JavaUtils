package pers.edebe.util.classloader;

import pers.edebe.util.base.ClassUtils;
import pers.edebe.util.io.FileType;
import pers.edebe.util.io.StreamUtils;

import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamClassLoader extends ZipClassLoader {
    public void add(ZipInputStream stream) throws IOException {
        StreamUtils.readAllEntry(stream, ((entry, resettable) -> {
            if (!entry.isDirectory() && FileType.CLASS.isThisFileSuffix(entry.getName()) && FileType.CLASS.isThisFileHeader(resettable)) {
                resettable.reset();
                classes.put(ClassUtils.toClassName(entry.getName()), StreamUtils.toByteArray(resettable));
            }
        }));
    }
}