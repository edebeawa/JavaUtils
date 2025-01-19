package top.edebe.util.io;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@UtilityClass
public class PathUtils {
    public static Path of(String first, String... more) {
        return FileSystems.getDefault().getPath(first, more);
    }

    public static @NotNull Path getPath(final @NotNull URL resource, final @NotNull Charset charset) throws UnsupportedEncodingException {
        String path = URLDecoder.decode(resource.getPath(), charset.name());
        if (path.startsWith("file:"))
            path = path.substring("file:".length());
        if (path.contains("!"))
            path = path.substring(0, path.indexOf("!"));
        if (path.startsWith("/"))
            path = path.substring(1);
        if (path.contains("#"))
            path = path.substring(0, path.lastIndexOf("#"));
        return PathUtils.of(path);
    }

    public static @NotNull Path getPath(final @NotNull URL resource, final @NotNull String charsetName) throws UnsupportedEncodingException {
        return PathUtils.getPath(resource, Charset.forName(charsetName));
    }

    public static @NotNull Path getPath(final @NotNull URL resource) throws UnsupportedEncodingException {
        return PathUtils.getPath(resource, Charset.defaultCharset());
    }
}