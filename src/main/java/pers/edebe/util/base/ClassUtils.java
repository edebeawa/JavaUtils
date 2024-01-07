package pers.edebe.util.base;

import pers.edebe.util.io.PathUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

    public static Path getPath(ClassLoader classloader, String classname, Charset charset) throws IOException {
        URL resource = classloader.getResource(classname.replace('.', '/') + ".class");
        if (resource != null) {
            return PathUtils.getPath(resource, charset);
        } else {
            throw new FileNotFoundException();
        }
    }

    public static Path getPath(Class<?> clazz, Charset charset) throws IOException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charset);
    }

    public static Path getPath(ClassLoader classloader, String classname, String charsetName) throws IOException {
        return getPath(classloader, classname, Charset.forName(charsetName));
    }

    public static Path getPath(Class<?> clazz, String charsetName) throws IOException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charsetName);
    }

    public static Path getPath(ClassLoader classloader, String classname) throws IOException {
        return getPath(classloader, classname, Charset.defaultCharset());
    }

    public static Path getPath(Class<?> clazz) throws IOException {
        return getPath(clazz.getClassLoader(), clazz.getName());
    }
}