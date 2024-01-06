package pers.edebe.util.base;

import java.util.List;

public final class TypeMap {
    private static final List<Class<?>> PRIMITIVE_CLASSES = List.of(
            Void.TYPE,
            Boolean.TYPE,
            Byte.TYPE,
            Character.TYPE,
            Double.TYPE,
            Float.TYPE,
            Integer.TYPE,
            Long.TYPE,
            Short.TYPE
    );
    private static final List<Class<?>> WRAP_CLASSES = List.of(
            Void.class,
            Boolean.class,
            Byte.class,
            Character.class,
            Double.class,
            Float.class,
            Integer.class,
            Long.class,
            Short.class
    );

    public static boolean isPrimitiveClass(Class<?> clazz) {
        return PRIMITIVE_CLASSES.contains(clazz);
    }

    public static boolean isWrapClass(Class<?> clazz) {
        return WRAP_CLASSES.contains(clazz);
    }

    private static Class<?> getPrimitiveClass0(Class<?> clazz) {
        return PRIMITIVE_CLASSES.get(WRAP_CLASSES.indexOf(clazz));
    }

    public static Class<?> getPrimitiveClass(Class<?> clazz) {
        if (isWrapClass(clazz)) {
            return getPrimitiveClass0(clazz);
        } else {
            throw new IllegalArgumentException(clazz.getName());
        }
    }

    private static Class<?> getWrapClass0(Class<?> clazz) {
        return WRAP_CLASSES.get(PRIMITIVE_CLASSES.indexOf(clazz));
    }

    public static Class<?> getWrapClass(Class<?> clazz) {
        if (isPrimitiveClass(clazz)) {
            return getWrapClass0(clazz);
        } else {
            throw new IllegalArgumentException(clazz.getName());
        }
    }

    public static Class<?> getPrimitiveClassOrOther(Class<?> clazz, Class<?> other) {
        if (isWrapClass(clazz)) {
            return getPrimitiveClass0(clazz);
        } else {
            return other;
        }
    }

    public static Class<?> getWrapClassOrOther(Class<?> clazz, Class<?> other) {
        if (isPrimitiveClass(clazz)) {
            return getWrapClass0(clazz);
        } else {
            return other;
        }
    }

    public static Class<?> getPrimitiveClassOrReturn(Class<?> clazz) {
        return getPrimitiveClassOrOther(clazz, clazz);
    }

    public static Class<?> getWrapClassOrReturn(Class<?> clazz) {
        return getWrapClassOrOther(clazz, clazz);
    }
}