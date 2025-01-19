package top.edebe.util.io;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;

@UtilityClass
public class PathUtils {
    public static @NotNull Path getPath(final @NotNull URL resource, final @NotNull Charset charset) {
        String path = URLDecoder.decode(resource.getPath(), charset);
        if (path.startsWith("file:"))
            path = path.substring("file:".length());
        if (path.contains("!"))
            path = path.substring(0, path.indexOf("!"));
        if (path.startsWith("/"))
            path = path.substring(1);
        if (path.contains("#"))
            path = path.substring(0, path.lastIndexOf("#"));
        return Path.of(path);
    }

    public static @NotNull Path getPath(final @NotNull URL resource, final @NotNull String charsetName) {
        return PathUtils.getPath(resource, Charset.forName(charsetName));
    }

    public static @NotNull Path getPath(final @NotNull URL resource) {
        return PathUtils.getPath(resource, Charset.defaultCharset());
    }
}