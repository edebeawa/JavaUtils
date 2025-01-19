package top.edebe.util.io;

import lombok.experimental.UtilityClass;
import top.edebe.util.function.ThrowableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@UtilityClass
public class StreamUtils {
    public static void writeAll(final @NotNull InputStream inputStream, final @NotNull OutputStream outputStream) throws IOException {
        int result;
        while ((result = inputStream.read()) != -1) outputStream.write(result);
    }

    public static @NotNull ByteArrayOutputStream toByteArrayOutputStream(final @NotNull InputStream stream) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        StreamUtils.writeAll(stream, output);
        return output;
    }

    public static @NotNull ByteArrayInputStream toByteArrayInputStream(final @NotNull InputStream stream) throws IOException {
        return new ByteArrayInputStream(StreamUtils.toByteArray(stream));
    }

    public static @NotNull String toString(final @NotNull InputStream stream) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString();
    }

    public static @NotNull String toString(final @NotNull InputStream stream, final @NotNull String charsetName) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString(charsetName);
    }

    public static @NotNull String toString(final @NotNull InputStream stream, final @NotNull Charset charset) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toString(charset);
    }

    public static byte[] toByteArray(final @NotNull InputStream stream) throws IOException {
        return StreamUtils.toByteArrayOutputStream(stream).toByteArray();
    }

    public static byte @NotNull [] toByteArray(final char @NotNull [] chars, final @NotNull Charset charset) {
        final CharBuffer charBuffer = CharBuffer.allocate(chars.length);
        charBuffer.put(chars);
        charBuffer.flip();
        return charset.encode(charBuffer).array();
    }

    public static void writeAllLine(final @NotNull BufferedWriter writer, final @NotNull List<String> lines) throws IOException {
        for (final String line : lines) {
            writer.write(line);
            writer.newLine();
        }
    }

    public static void readAllEntry(final @NotNull ZipInputStream stream, final @NotNull ThrowableBiConsumer<ZipEntry, InputStream, IOException> consumer) throws IOException {
        ZipEntry entry;
        while ((entry = stream.getNextEntry()) != null)
            consumer.accept(entry, StreamUtils.toByteArrayInputStream(stream));
    }

    public static void writeAllEntry(final @NotNull ZipOutputStream stream, final @NotNull Map<ZipEntry, InputStream> map) throws IOException {
        for (final Map.Entry<ZipEntry, InputStream> entry : map.entrySet()) {
            stream.putNextEntry(entry.getKey());
            StreamUtils.writeAll(entry.getValue(), stream);
        }
    }
}