package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@UtilityClass
public class ThrowableUtils {
    public static @NotNull ByteArrayOutputStream getStackTraceByteArrayOutputStream(@NotNull Throwable throwable) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (final PrintStream print = new PrintStream(output)) {
            throwable.printStackTrace(print);
        }
        return output;
    }

    public static String getStackTraceString(@NotNull Throwable throwable) {
        return ThrowableUtils.getStackTraceByteArrayOutputStream(throwable).toString();
    }

    public static String getStackTraceString(@NotNull Throwable throwable, @NotNull String charsetName) throws UnsupportedEncodingException {
        return ThrowableUtils.getStackTraceByteArrayOutputStream(throwable).toString(charsetName);
    }

    public static String getStackTraceString(@NotNull Throwable throwable, @NotNull Charset charset) {
        return ThrowableUtils.getStackTraceByteArrayOutputStream(throwable).toString(charset);
    }

    public static byte[] getStackTraceByteArray(@NotNull Throwable throwable) {
        return ThrowableUtils.getStackTraceByteArrayOutputStream(throwable).toByteArray();
    }
}