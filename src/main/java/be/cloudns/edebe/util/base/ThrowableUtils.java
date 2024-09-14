package be.cloudns.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;

@UtilityClass
public class ThrowableUtils {
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T initCause(T throwable, Throwable cause) {
        return (T) throwable.initCause(cause);
    }

    public static String getStackTraceInfo(Throwable throwable) {
        try (ByteArrayPrintStream stream = new ByteArrayPrintStream()) {
            throwable.printStackTrace(stream);
            return stream.toString();
        }
    }

    private static class ByteArrayPrintStream extends PrintStream {
        private final StringBuilder builder = new StringBuilder();

        public ByteArrayPrintStream() {
            super(new ByteArrayOutputStream());
        }

        @Override
        public void print(boolean b) {
            builder.append(b);
        }

        @Override
        public void print(char c) {
            builder.append(c);
        }

        @Override
        public void print(int i) {
            builder.append(i);
        }

        @Override
        public void print(long l) {
            builder.append(l);
        }

        @Override
        public void print(float f) {
            builder.append(f);
        }

        @Override
        public void print(double d) {
            builder.append(d);
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public void print(char[] s) {
            builder.append(s);
        }

        @Override
        public void print(@Nullable String s) {
            builder.append(s);
        }

        @Override
        public void print(@Nullable Object obj) {
            builder.append(obj);
        }

        @Override
        public void println() {
            builder.append(System.lineSeparator());
        }

        @Override
        public void println(boolean x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(char x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(int x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(long x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(float x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(double x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public void println(char[] x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(@Nullable String x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public void println(@Nullable Object x) {
            builder.append(System.lineSeparator()).append(x);
        }

        @Override
        public PrintStream printf(@NotNull String format, Object... args) {
            return format(format, args);
        }

        @Override
        public PrintStream printf(Locale l, @NotNull String format, Object... args) {
            return format(l, format, args);
        }

        @Override
        public PrintStream format(@NotNull String format, Object... args) {
            return format(null, format, args);
        }

        @Override
        public PrintStream format(Locale l, @NotNull String format, Object... args) {
            builder.append(format);
            Arrays.stream(args).forEach(builder::append);
            return this;
        }

        @Override
        public PrintStream append(CharSequence csq) {
            builder.append(csq);
            return this;
        }

        @Override
        public PrintStream append(CharSequence csq, int start, int end) {
            builder.append(csq, start, end);
            return this;
        }

        @Override
        public PrintStream append(char c) {
            builder.append(c);
            return this;
        }

        public String toString() {
            return builder.toString();
        }
    }
}