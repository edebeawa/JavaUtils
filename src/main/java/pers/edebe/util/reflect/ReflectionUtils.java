package pers.edebe.util.reflect;

import org.jetbrains.annotations.Nullable;
import pers.edebe.util.base.ClassUtils;
import pers.edebe.util.misc.UnsafeUtils;
import pers.edebe.util.wrapper.AccessibleObjectWrapper;
import pers.edebe.util.wrapper.ClassWrapper;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@CallerSensitive
public final class ReflectionUtils {
    public static final Object REFLECTION_FACTORY_INSTANCE;
    public static final Class<?> REFLECTION_FACTORY_CLASS;
    private static final Method CLASS_FOR_NAME_METHOD;
    private static final Method SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD;

    static {
        try {
            REFLECTION_FACTORY_INSTANCE = getAccessibleDeclaredMethod(Class.class, "getReflectionFactory").invoke(null);
            REFLECTION_FACTORY_CLASS = REFLECTION_FACTORY_INSTANCE.getClass();
            CLASS_FOR_NAME_METHOD = getAccessibleDeclaredMethod(Class.class, "forName0", String.class, boolean.class, ClassLoader.class, Class.class);
            SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD = getAccessibleDeclaredMethod(SecurityManager.class, "getClassContext");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    //write directly into override to bypass perms
    public static void setAccessibleNoRestrict(AccessibleObject object, boolean flag) {
        UnsafeUtils.UNSAFE_INSTANCE.putBoolean(object, UnsafeUtils.ACCESS_MODIFIER_OFFSET, flag);
    }

    public static Field getAccessibleField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field = clazz.getField(name);
        setAccessibleNoRestrict(field, true);
        return field;
    }

    public static Method getAccessibleMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(name, parameterTypes);
        setAccessibleNoRestrict(method, true);
        return method;
    }

    public static <T> Constructor<T> getAccessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> constructor = clazz.getConstructor(parameterTypes);
        setAccessibleNoRestrict(constructor, true);
        return constructor;
    }

    public static Field getAccessibleDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(name);
        setAccessibleNoRestrict(field, true);
        return field;
    }

    public static Method getAccessibleDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        setAccessibleNoRestrict(method, true);
        return method;
    }

    public static <T> Constructor<T> getAccessibleDeclaredConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
        setAccessibleNoRestrict(constructor, true);
        return constructor;
    }

    public static Class<?> getClassForName(String name, boolean initialize, ClassLoader loader, Class<?> caller) throws ReflectiveOperationException {
        return (Class<?>) CLASS_FOR_NAME_METHOD.invoke(null, name, initialize, loader, caller);
    }

    public static Class<?> getClassForName(String name, boolean initialize) throws ReflectiveOperationException {
        Class<?> caller = getCallerClass();
        if (caller != null) {
            return getClassForName(name, initialize, caller.getClassLoader(), caller);
        } else {
            throw new ClassNotFoundException();
        }
    }

    public static Class<?> getClassForName(String name) throws ReflectiveOperationException {
        return getClassForName(name, true);
    }

    public static <T extends Enum<T>> T newEnum(Class<T> type, String name, int ordinal, Object... arguments) throws ReflectiveOperationException {
        List<Object> list = new ArrayList<>();
        list.add(name);
        list.add(ordinal);
        list.addAll(Arrays.asList(arguments));
        Object[] objects = list.toArray();
        return ClassWrapper.wrap(type)
                .getDeclaredConstructorNoRestrictFuzzyMatch(ClassUtils.getClass(objects))
                .setAccessibleNoRestrict(true)
                .setType(type)
                .newInstanceNoRestrict(objects);
    }

    @Nullable
    public static Class<?> getCallerClass(int depth) {
        try {
            Class<?>[] classes = (Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager());
            if (depth > 0 && depth < classes.length) {
                return classes[depth];
            }
        } catch (ReflectiveOperationException e) {
            //no op
        }
        return null;
    }

    @Nullable
    public static Class<?> getCallerClass() {
        try {
            Class<?>[] classes = (Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager());
            for (Class<?> clazz : classes) {
                Class<?> superclass = clazz.getSuperclass();
                if (!(clazz.isAnnotationPresent(CallerSensitive.class) || (superclass != null && superclass.equals(AccessibleObjectWrapper.class)))) {
                    return clazz;
                }
            }
        } catch (ReflectiveOperationException e) {
            //no op
        }
        return null;
    }
}