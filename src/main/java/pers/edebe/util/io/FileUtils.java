package pers.edebe.util.io;

import pers.edebe.util.base.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class FileUtils {
    public static byte[] getByteArray(File file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return StreamUtils.toByteArray(stream);
        }
    }

    public static byte[] getByteArray(Path path) throws IOException {
        return getByteArray(path.toFile());
    }

    public static String getString(File file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return StreamUtils.toString(stream);
        }
    }

    public static String getString(Path path) throws IOException {
        return getString(path.toFile());
    }

    public static boolean isFileSuffixEquals(String name, String suffix) {
        return StringUtils.toLowerCase(name.substring(name.lastIndexOf(".") + 1)).equals(StringUtils.toLowerCase(suffix));
    }

    public static boolean isFileSuffixEquals(File file, String suffix) {
        return isFileSuffixEquals(file.getName(), suffix);
    }

    public static boolean isFileSuffixEquals(Path path, String suffix) {
        return isFileSuffixEquals(path.toFile(), suffix);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isFileHeaderEquals(InputStream stream, byte[] magic) throws IOException {
        int length = magic.length;
        byte[] buffer = new byte[length];
        stream.read(buffer, 0, length);
        return Arrays.equals(buffer, magic);
    }

    public static boolean isFileHeaderEquals(File file, byte[] magic) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return isFileHeaderEquals(stream, magic);
        }
    }

    public static boolean isFileHeaderEquals(Path path, byte[] magic) throws IOException {
        return isFileHeaderEquals(path.toFile(), magic);
    }

    public static boolean isFileTypeEquals(ZipFile file, ZipEntry entry, String suffix, byte[] magic) throws IOException {
        return !entry.isDirectory() && isFileSuffixEquals(entry.getName(), suffix) && isFileHeaderEquals(file.getInputStream(entry), magic);
    }

    public static boolean isFileTypeEquals(File file, String suffix, byte[] magic) throws IOException {
        return !file.isDirectory() && isFileSuffixEquals(file, suffix) && isFileHeaderEquals(file, magic);
    }

    public static boolean isFileTypeEquals(Path path, String suffix, byte[] magic) throws IOException {
        return isFileTypeEquals(path.toFile(), suffix, magic);
    }

    public static boolean isFileMessageDigestEquals(byte[] bytes0, byte[] bytes1, MessageDigest digest) {
        return Arrays.equals(digest.digest(bytes0), digest.digest(bytes1));
    }

    public static boolean isFileMessageDigestEquals(byte[] bytes0, byte[] bytes1, String digest) throws NoSuchAlgorithmException {
        return isFileMessageDigestEquals(bytes0, bytes1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(InputStream stream0, InputStream stream1, MessageDigest digest) throws IOException {
        return isFileMessageDigestEquals(StreamUtils.toByteArray(stream0), StreamUtils.toByteArray(stream1), digest);
    }

    public static boolean isFileMessageDigestEquals(InputStream stream0, InputStream stream1, String digest) throws IOException, NoSuchAlgorithmException {
        return isFileMessageDigestEquals(stream0, stream1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(File file0, File file1, MessageDigest digest) throws IOException {
        try (FileInputStream stream0 = new FileInputStream(file0); FileInputStream stream1 = new FileInputStream(file1)) {
            return isFileMessageDigestEquals(stream0, stream1, digest);
        }
    }

    public static boolean isFileMessageDigestEquals(File file0, File file1, String digest) throws IOException, NoSuchAlgorithmException {
        return isFileMessageDigestEquals(file0, file1, MessageDigest.getInstance(digest));
    }

    public static boolean isFileMessageDigestEquals(Path path0, Path path1, MessageDigest digest) throws IOException {
        return isFileMessageDigestEquals(path0.toFile(), path1.toFile(), digest);
    }

    public static boolean isFileMessageDigestEquals(Path path0, Path path1, String digest) throws IOException, NoSuchAlgorithmException {
        return isFileMessageDigestEquals(path0, path1, MessageDigest.getInstance(digest));
    }
}