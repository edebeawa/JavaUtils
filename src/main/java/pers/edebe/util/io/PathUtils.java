package pers.edebe.util.io;

import lombok.experimental.UtilityClass;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;

@UtilityClass
public class PathUtils {
    public static Path getPath(URL resource, Charset charset) {
        String path = URLDecoder.decode(resource.getPath(), charset);
        if (path.startsWith("file:")) {
            path = path.substring("file:".length());
        }
        if (path.contains("!")) {
            path = path.substring(0, path.indexOf("!"));
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.contains("#")) {
            path = path.substring(0, path.lastIndexOf("#"));
        }
        return Path.of(path);
    }

    public static Path getPath(URL resource, String charsetName) {
        return getPath(resource, Charset.forName(charsetName));
    }

    public static Path getPath(URL resource) {
        return getPath(resource, Charset.defaultCharset());
    }
}