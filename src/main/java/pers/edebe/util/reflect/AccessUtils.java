package pers.edebe.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessUtils {
    public static Field getAccessibleField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field = clazz.getField(name);
        ReflectionUtils.setAccessibleNoRestrict(field, true);
        return field;
    }

    public static Method getAccessibleMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(name, parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(method, true);
        return method;
    }

    public static <T> Constructor<T> getAccessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> constructor = clazz.getConstructor(parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(constructor, true);
        return constructor;
    }

    public static Field getAccessibleDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(name);
        ReflectionUtils.setAccessibleNoRestrict(field, true);
        return field;
    }

    public static Method getAccessibleDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(method, true);
        return method;
    }

    public static <T> Constructor<T> getAccessibleDeclaredConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(constructor, true);
        return constructor;
    }
}