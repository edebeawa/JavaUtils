package top.edebe.util.io;

import top.edebe.util.base.StringUtils;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@UtilityClass
public class FileUtils {
    public static @NotNull Path createDirectory(final @NotNull Path dir) throws IOException {
        final File file = dir.toFile();
        if (file.exists() || file.mkdir())
            return dir;
        else
            throw new FileSystemException(dir.toString(), null, "Unable to create directory");
    }

    public static byte @NotNull [] getByteArray(final @NotNull File file) throws IOException {
        try (final FileInputStream stream = new FileInputStream(file)) {
            return StreamUtils.toByteArray(stream);
        }
    }

    public static byte @NotNull [] getByteArray(final @NotNull Path path) throws IOException {
        return FileUtils.getByteArray(path.toFile());
    }

    public static @NotNull String getString(final @NotNull File file) throws IOException {
        try (final FileInputStream stream = new FileInputStream(file)) {
            return StreamUtils.toString(stream);
        }
    }

    public static @NotNull String getString(final @NotNull Path path) throws IOException {
        return FileUtils.getString(path.toFile());
    }

    public static boolean isFileSuffixEquals(final @NotNull String name, final @NotNull String suffix) {
        return StringUtils.toLowerCase(name.substring(name.lastIndexOf(".") + 1)).equals(StringUtils.toLowerCase(suffix));
    }

    public static boolean isFileSuffixEquals(final @NotNull File file, final @NotNull String suffix) {
        return FileUtils.isFileSuffixEquals(file.getName(), suffix);
    }

    public static boolean isFileSuffixEquals(final @NotNull Path path, final @NotNull String suffix) {
        return FileUtils.isFileSuffixEquals(path.toFile(), suffix);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isFileHeaderEquals(final @NotNull InputStream stream, final byte @NotNull [] magic) throws IOException {
        final int length = magic.length;
        final byte[] buffer = new byte[length];
        stream.read(buffer, 0, length);
        return Arrays.equals(buffer, magic);
    }

    public static boolean isFileHeaderEquals(final @NotNull File file, final byte @NotNull [] magic) throws IOException {
        try (final FileInputStream stream = new FileInputStream(file)) {
            return FileUtils.isFileHeaderEquals(stream, magic);
        }
    }

    public static boolean isFileHeaderEquals(final @NotNull Path path, final byte @NotNull [] magic) throws IOException {
        return FileUtils.isFileHeaderEquals(path.toFile(), magic);
    }

    public static boolean isFileTypeEquals(final @NotNull ZipFile file, final @NotNull ZipEntry entry, final @NotNull String suffix, final byte @NotNull [] magic) throws IOException {
        return !entry.isDirectory() && FileUtils.isFileSuffixEquals(entry.getName(), suffix) && FileUtils.isFileHeaderEquals(file.getInputStream(entry), magic);
    }

    public static boolean isFileTypeEquals(final @NotNull File file, final @NotNull String suffix, final byte @NotNull [] magic) throws IOException {
        return !file.isDirectory() && FileUtils.isFileSuffixEquals(file, suffix) && FileUtils.isFileHeaderEquals(file, magic);
    }

    public static boolean isFileTypeEquals(final @NotNull Path path, final @NotNull String suffix, final byte @NotNull [] magic) throws IOException {
        return FileUtils.isFileTypeEquals(path.toFile(), suffix, magic);
    }

    public static boolean isFileMessageDigestEquals(final byte @NotNull [] bytes0, final byte @NotNull [] bytes1, final @NotNull MessageDigest digest) {
        return Arrays.equals(digest.digest(bytes0), digest.digest(bytes1));
    }

    public static boolean isFileMessageDigestEquals(final byte @NotNull [] bytes0, final byte @NotNull [] bytes1, final @NotNull String digest) throws NoSuchAlgorithmException {
        return FileUtils.isFileMessageDigestEquals(bytes0, bytes1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(final @NotNull InputStream stream0, final @NotNull InputStream stream1, final @NotNull MessageDigest digest) throws IOException {
        return FileUtils.isFileMessageDigestEquals(StreamUtils.toByteArray(stream0), StreamUtils.toByteArray(stream1), digest);
    }

    public static boolean isFileMessageDigestEquals(final @NotNull InputStream stream0, final @NotNull InputStream stream1, final @NotNull String digest) throws IOException, NoSuchAlgorithmException {
        return FileUtils.isFileMessageDigestEquals(stream0, stream1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(final @NotNull File file0, final @NotNull File file1, final @NotNull MessageDigest digest) throws IOException {
        try (final FileInputStream stream0 = new FileInputStream(file0); final FileInputStream stream1 = new FileInputStream(file1)) {
            return FileUtils.isFileMessageDigestEquals(stream0, stream1, digest);
        }
    }

    public static boolean isFileMessageDigestEquals(final @NotNull File file0, final @NotNull File file1, final @NotNull String digest) throws IOException, NoSuchAlgorithmException {
        return FileUtils.isFileMessageDigestEquals(file0, file1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(final @NotNull Path path0, final @NotNull Path path1, final @NotNull MessageDigest digest) throws IOException {
        return FileUtils.isFileMessageDigestEquals(path0.toFile(), path1.toFile(), digest);
    }

    public static boolean isFileMessageDigestEquals(final @NotNull Path path0, final @NotNull Path path1, final @NotNull String digest) throws IOException, NoSuchAlgorithmException {
        return FileUtils.isFileMessageDigestEquals(path0, path1, MessageDigest.getInstance(digest));
    }
}