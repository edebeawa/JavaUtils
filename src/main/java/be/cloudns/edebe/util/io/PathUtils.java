package be.cloudns.edebe.util.io;

import lombok.experimental.UtilityClass;

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

    public static Path getPath(URL resource, Charset charset) throws UnsupportedEncodingException {
        String path = URLDecoder.decode(resource.getPath(), charset.name());
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
        return of(path);
    }

    public static Path getPath(URL resource, String charsetName) throws UnsupportedEncodingException {
        return getPath(resource, Charset.forName(charsetName));
    }

    public static Path getPath(URL resource) throws UnsupportedEncodingException {
        return getPath(resource, Charset.defaultCharset());
    }
}
