package pers.edebe.util.io;

import pers.edebe.util.function.BiIOConsumer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class StreamUtils {
    public static void writeAll(InputStream inputStream, OutputStream outputStream) throws IOException {
        int result;
        while ((result = inputStream.read()) != -1) outputStream.write(result);
    }

    public static ByteArrayOutputStream toByteArrayOutputStream(InputStream stream) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            writeAll(stream, output);
            return output;
        }
    }

    public static String toString(InputStream stream) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString();
    }

    public static String toString(InputStream stream, String charsetName) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString(charsetName);
    }

    public static String toString(InputStream stream, Charset charset) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString(charset.name());
    }

    public static byte[] toByteArray(InputStream stream) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toByteArray();
    }

    public static byte[] toByteArray(char[] chars, Charset charset) {
        CharBuffer charBuffer = CharBuffer.allocate(chars.length);
        charBuffer.put(chars);
        charBuffer.flip();
        ByteBuffer byteBuffer = charset.encode(charBuffer);
        return byteBuffer.array();
    }

    public static void writeAllLine(BufferedWriter writer, List<String> lines) throws IOException {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
    }

    public static void readAllEntry(ZipInputStream stream, BiIOConsumer<ZipEntry, ResettableInputStream> consumer) throws IOException {
        ZipEntry entry;
        while ((entry = stream.getNextEntry()) != null)
            consumer.accept(entry, new ResettableInputStream(stream));
    }

    public static void writeAllEntry(ZipOutputStream stream, Map<ZipEntry, InputStream> map) throws IOException {
        for (Entry<ZipEntry, InputStream> entry : map.entrySet()) {
            stream.putNextEntry(entry.getKey());
            writeAll(entry.getValue(), stream);
        }
    }
}