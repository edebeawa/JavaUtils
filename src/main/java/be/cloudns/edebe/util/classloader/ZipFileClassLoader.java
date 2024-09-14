package be.cloudns.edebe.util.classloader;

import be.cloudns.edebe.util.base.ClassUtils;
import be.cloudns.edebe.util.io.FileType;
import be.cloudns.edebe.util.io.StreamUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileClassLoader extends ZipClassLoader {
    public void add(ZipFile file) throws IOException {
        Enumeration<? extends ZipEntry> enumeration = file.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry entry = enumeration.nextElement();
            if (FileType.CLASS.isThisFileType(file, entry))
                classes.put(ClassUtils.toClassName(entry.getName()), StreamUtils.toByteArray(file.getInputStream(entry)));
        }
    }
}