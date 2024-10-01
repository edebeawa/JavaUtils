package be.cloudns.edebe.util.base;

import be.cloudns.edebe.util.exception.ResourceNotFoundException;
import lombok.experimental.UtilityClass;
import be.cloudns.edebe.util.io.ClassResourceContext;
import be.cloudns.edebe.util.io.PathUtils;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;

@UtilityClass
public class ClassUtils {
    public static Class<?>[] getClass(Object[] objects) {
        return Arrays.stream(objects).map(Object::getClass).toArray(Class[]::new);
    }

    public static String toBinaryName(String classname) {
        return classname.replace('/', '.');
    }

    public static String toInternalName(String classname) {
        return classname.replace('.', '/');
    }

    public static String getInternalName(Class<?> clazz) {
        return toInternalName(clazz.getName());
    }

    public static String toClassName(String classpath) {
        return toBinaryName(classpath.substring(0, classpath.lastIndexOf(".")));
    }

    public static String toClassPath(String classname) {
        return toInternalName(classname) + ".class";
    }

    public static String toClassPath(Class<?> clazz) {
        return toClassPath(clazz.getName());
    }

    public static Path getPath(ClassResourceContext context, String classname, Charset charset) throws ResourceNotFoundException {
        return PathUtils.getPath(context.getResource(toClassPath(classname)), charset);
    }

    public static Path getPath(ClassLoader classloader, String classname, Charset charset) throws ResourceNotFoundException {
        return getPath(new ClassResourceContext(classloader), classname, charset);
    }

    public static Path getPath(Class<?> clazz, Charset charset) throws ResourceNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charset);
    }

    public static Path getPath(ClassLoader classloader, String classname, String charsetName) throws ResourceNotFoundException {
        return getPath(classloader, classname, Charset.forName(charsetName));
    }

    public static Path getPath(Class<?> clazz, String charsetName) throws ResourceNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName(), charsetName);
    }

    public static Path getPath(ClassLoader classloader, String classname) throws ResourceNotFoundException {
        return getPath(classloader, classname, Charset.defaultCharset());
    }

    public static Path getPath(Class<?> clazz) throws ResourceNotFoundException {
        return getPath(clazz.getClassLoader(), clazz.getName());
    }
}