package top.edebe.util.io;

import top.edebe.util.exception.ResourceNotFoundException;
import top.edebe.util.loader.ClassLoaderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Function;

public class ClassResourceContext {
    private final @NotNull Function<String, URL> getResourceFunction;
    private final @NotNull Function<String, InputStream> getResourceAsStreamFunction;

    public ClassResourceContext(final @NotNull Class<?> clazz) {
        this.getResourceFunction = clazz::getResource;
        this.getResourceAsStreamFunction = clazz::getResourceAsStream;
    }

    public ClassResourceContext(final @Nullable ClassLoader loader) {
        final ClassLoader cl = ClassLoaderUtils.requireNonNull(loader);
        this.getResourceFunction = cl::getResource;
        this.getResourceAsStreamFunction = cl::getResourceAsStream;
    }

    public @NotNull URL getResource(final @NotNull String name) throws ResourceNotFoundException {
        final URL url = this.getResourceFunction.apply(name);
        if (url != null)
            return url;
        else
            throw new ResourceNotFoundException(name);
    }

    public @NotNull InputStream getResourceAsStream(final @NotNull String name) throws ResourceNotFoundException {
        final InputStream stream = this.getResourceAsStreamFunction.apply(name);
        if (stream != null)
            return stream;
        else
            throw new ResourceNotFoundException(name);
    }

    public @NotNull File getResourceAsTempFile(final @NotNull String name, final @NotNull String prefix, final @Nullable String suffix, @Nullable File directory) throws IOException {
        final File file = File.createTempFile(prefix, suffix, directory);
        try (final InputStream inputStream = this.getResourceAsStream(name); final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(StreamUtils.toByteArray(inputStream));
        }
        file.deleteOnExit();
        return file;
    }

    public @NotNull File getResourceAsTempFile(final @NotNull String name, final @NotNull String prefix, final @Nullable String suffix) throws IOException {
        return this.getResourceAsTempFile(name, prefix, suffix, null);
    }

    public @NotNull String getResourceAsString(final @NotNull String name) throws IOException {
        return StreamUtils.toString(this.getResourceAsStream(name));
    }

    public @NotNull String getResourceAsString(final @NotNull String name, final @NotNull String charsetName) throws IOException {
        return StreamUtils.toString(this.getResourceAsStream(name), charsetName);
    }

    public @NotNull String getResourceAsString(final @NotNull String name, final @NotNull Charset charset) throws IOException {
        return StreamUtils.toString(this.getResourceAsStream(name), charset);
    }
}