package pers.edebe.util.base;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;

public final class ClassUtils {
    public static Class<?>[] getClass(Object[] objects) {
        return Arrays.stream(objects).map(Object::getClass).toArray(Class[]::new);
    }

    public static boolean equals(Object[] array0, Object[] array1, boolean fuzzy) {
        return ArrayUtils.equals(
                Arrays.stream(array0).map(Object::getClass).toArray(Class[]::new),
                Arrays.stream(array1).map(Object::getClass).toArray(Class[]::new),
                fuzzy
        );
    }

    public static String toBinaryName(String classname) {
        return classname.replace('/', '.');
    }

    public static String toInternalName(String classname) {
        return classname.replace('.', '/');
    }

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

    public static Path getPath(ClassLoader classloader, String classname, Charset charset) throws FileNotFoundException {
        URL resource = classloader.getResource(classname.replace('.', '/') + ".class");
        if (resource != null) {
            return getPath(resource, charset);
        } else {
            throw new FileNotFoundException();
        }
    }

    public static Path getPath(Class<?> clazz, Charset charset) throws FileNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charset);
    }

    public static Path getPath(ClassLoader classloader, String classname, String charsetName) throws FileNotFoundException {
        return getPath(classloader, classname, Charset.forName(charsetName));
    }

    public static Path getPath(Class<?> clazz, String charsetName) throws FileNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charsetName);
    }

    public static Path getPath(ClassLoader classloader, String classname) throws FileNotFoundException {
        return getPath(classloader, classname, Charset.defaultCharset());
    }

    public static Path getPath(Class<?> clazz) throws FileNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName());
    }
}