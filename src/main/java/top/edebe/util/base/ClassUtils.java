package top.edebe.util.base;

import top.edebe.util.exception.ResourceNotFoundException;
import lombok.experimental.UtilityClass;
import top.edebe.util.io.ClassResourceContext;
import top.edebe.util.io.PathUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;

@UtilityClass
public class ClassUtils {
    public static @NotNull Class<?> @NotNull [] getClasses(final @NotNull Object @NotNull [] objects) {
        return Arrays.stream(objects).map(Object::getClass).toArray(Class[]::new);
    }

    public static @NotNull String toBinaryName(final @NotNull String classname) {
        return classname.replace('/', '.');
    }

    public static @NotNull String toInternalName(final @NotNull String classname) {
        return classname.replace('.', '/');
    }

    public static @NotNull String getInternalName(final @NotNull Class<?> clazz) {
        return ClassUtils.toInternalName(clazz.getName());
    }

    public static @NotNull String toClassName(final @NotNull String classpath) {
        return ClassUtils.toBinaryName(classpath.substring(0, classpath.lastIndexOf(".")));
    }

    public static @NotNull String toClassPath(final @NotNull String classname) {
        return ClassUtils.toInternalName(classname).concat(".class");
    }

    public static @NotNull String toClassPath(final @NotNull Class<?> clazz) {
        return ClassUtils.toClassPath(clazz.getName());
    }

    public static @NotNull Path getPath(final @NotNull ClassResourceContext context, final @NotNull String classname, final @NotNull Charset charset) throws ResourceNotFoundException {
        return PathUtils.getPath(context.getResource(ClassUtils.toClassPath(classname)), charset);
    }

    public static @NotNull Path getPath(final @Nullable ClassLoader classloader, final @NotNull String classname, final @NotNull Charset charset) throws ResourceNotFoundException {
        return ClassUtils.getPath(new ClassResourceContext(classloader), classname, charset);
    }

    public static @NotNull Path getPath(final @NotNull Class<?> clazz, final @NotNull Charset charset) throws ResourceNotFoundException {
        return ClassUtils.getPath(clazz.getClassLoader(), clazz.getName(), charset);
    }

    public static @NotNull Path getPath(final @Nullable ClassLoader classloader, final @NotNull String classname, final @NotNull String charsetName) throws ResourceNotFoundException {
        return ClassUtils.getPath(classloader, classname, Charset.forName(charsetName));
    }

    public static @NotNull Path getPath(final @NotNull Class<?> clazz, final @NotNull String charsetName) throws ResourceNotFoundException {
        return ClassUtils.getPath(clazz.getClassLoader(), clazz.getName(), charsetName);
    }

    public static @NotNull Path getPath(final @Nullable ClassLoader classloader, final @NotNull String classname) throws ResourceNotFoundException {
        return ClassUtils.getPath(classloader, classname, Charset.defaultCharset());
    }

    public static @NotNull Path getPath(final @NotNull Class<?> clazz) throws ResourceNotFoundException {
        return ClassUtils.getPath(clazz.getClassLoader(), clazz.getName());
    }
}