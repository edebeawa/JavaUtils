package pers.edebe.util.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Getter
@AllArgsConstructor
public enum FileType {
    ZIP(0x50, 0x4B, 0x03, 0x04),
    JAR(0x50, 0x4B, 0x03, 0x04),
    RAR(0x52, 0x61, 0x72, 0x21, 0x1A, 0x07),
    CLASS(0xCA, 0xFE, 0xBA, 0xBE);

    private final byte[] bytes;

    FileType(int... integers) {
        this(toByteArray(integers));
    }

    private static byte[] toByteArray(int[] integers) {
        int length = integers.length;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) integers[i];
        }
        return bytes;
    }

    public boolean isThisFileSuffix(String name) {
        return FileUtils.isFileSuffixEquals(name, name());
    }

    public boolean isThisFileSuffix(File file) {
        return FileUtils.isFileSuffixEquals(file, name());
    }

    public boolean isThisFileSuffix(Path path) {
        return FileUtils.isFileSuffixEquals(path, name());
    }

    public boolean isThisFileHeader(InputStream stream) throws IOException {
        return FileUtils.isFileHeaderEquals(stream, getBytes());
    }

    public boolean isThisFileHeader(File file) throws IOException {
        return FileUtils.isFileHeaderEquals(file, getBytes());
    }

    public boolean isThisFileHeader(Path path) throws IOException {
        return FileUtils.isFileHeaderEquals(path, getBytes());
    }

    public boolean isThisFileType(ZipFile file, ZipEntry entry) throws IOException {
        return FileUtils.isFileTypeEquals(file, entry, name(), getBytes());
    }

    public boolean isThisFileType(File file) throws IOException {
        return FileUtils.isFileTypeEquals(file, name(), getBytes());
    }

    public boolean isThisFileType(Path path) throws IOException {
        return FileUtils.isFileTypeEquals(path, name(), getBytes());
    }
}
