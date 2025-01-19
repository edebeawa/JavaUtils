package top.edebe.util.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Getter
@AllArgsConstructor
public enum FileType {
    JPEG(0xFF, 0xD8, 0xFF),
    PNG(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A),
    GIF(0x47, 0x49, 0x46, 0x38, 0x39, 0x61),
    BMP(0x42, 0x4D),
    ZIP(0x50, 0x4B, 0x03, 0x04),
    JAR(0x50, 0x4B, 0x03, 0x04),
    RAR(0x52, 0x61, 0x72, 0x21, 0x1A, 0x07),
    CLASS(0xCA, 0xFE, 0xBA, 0xBE);

    private final byte @NotNull [] bytes;

    FileType(final int @NotNull ... integers) {
        this(toByteArray(integers));
    }

    private static byte @NotNull [] toByteArray(final int @NotNull [] integers) {
        final int length = integers.length;
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) bytes[i] = (byte) integers[i];
        return bytes;
    }

    public boolean isThisFileSuffix(final @NotNull String name) {
        return FileUtils.isFileSuffixEquals(name, this.name());
    }

    public boolean isThisFileSuffix(final @NotNull File file) {
        return FileUtils.isFileSuffixEquals(file, this.name());
    }

    public boolean isThisFileSuffix(final @NotNull Path path) {
        return FileUtils.isFileSuffixEquals(path, this.name());
    }

    public boolean isThisFileHeader(final @NotNull InputStream stream) throws IOException {
        return FileUtils.isFileHeaderEquals(stream, this.getBytes());
    }

    public boolean isThisFileHeader(final @NotNull File file) throws IOException {
        return FileUtils.isFileHeaderEquals(file, this.getBytes());
    }

    public boolean isThisFileHeader(final @NotNull Path path) throws IOException {
        return FileUtils.isFileHeaderEquals(path, this.getBytes());
    }

    public boolean isThisFileType(final @NotNull ZipFile file, final @NotNull ZipEntry entry) throws IOException {
        return FileUtils.isFileTypeEquals(file, entry, this.name(), this.getBytes());
    }

    public boolean isThisFileType(final @NotNull File file) throws IOException {
        return FileUtils.isFileTypeEquals(file, this.name(), this.getBytes());
    }

    public boolean isThisFileType(final @NotNull Path path) throws IOException {
        return FileUtils.isFileTypeEquals(path, this.name(), this.getBytes());
    }
}
