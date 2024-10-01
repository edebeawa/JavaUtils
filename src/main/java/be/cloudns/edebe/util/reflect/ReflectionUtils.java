package be.cloudns.edebe.util.reflect;

import be.cloudns.edebe.util.base.ClassUtils;
import be.cloudns.edebe.util.base.ThrowableUtils;
import be.cloudns.edebe.util.misc.UnsafeUtils;
import be.cloudns.edebe.util.wrapper.AccessibleObjectWrapper;
import be.cloudns.edebe.util.wrapper.ClassWrapper;
import be.cloudns.edebe.util.wrapper.ConstructorWrapper;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@UtilityClass
@CallerSensitive
public class ReflectionUtils {
    private static final long CLASS_TYPE_OFFSET = 72;
    private static final long ELEMENT_TYPE_OFFSET = 8;
    private static final Method CLASS_FOR_NAME_METHOD;
    private static final Method SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD;
    private static final Method STACK_STREAM_FACTORY_IS_METHOD_HANDLE_FRAME_METHOD;
    private static final Method STACK_STREAM_FACTORY_IS_REFLECTION_FRAME_METHOD;

    static {
        try {
            CLASS_FOR_NAME_METHOD = getAccessibleDeclaredMethod(Class.class, "forName0", String.class, boolean.class, ClassLoader.class, Class.class);
            SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD = getAccessibleDeclaredMethod(SecurityManager.class, "getClassContext");
            final Class<?> classStackStreamFactory = Class.forName("java.lang.StackStreamFactory");
            STACK_STREAM_FACTORY_IS_METHOD_HANDLE_FRAME_METHOD = getAccessibleDeclaredMethod(classStackStreamFactory, "isMethodHandleFrame", Class.class);
            STACK_STREAM_FACTORY_IS_REFLECTION_FRAME_METHOD = getAccessibleDeclaredMethod(classStackStreamFactory, "isReflectionFrame", Class.class);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyInt(Object object0, Object object1, long offset) {
        UnsafeUtils.UNSAFE_INSTANCE.putInt(object0, offset, UnsafeUtils.UNSAFE_INSTANCE.getInt(object1, offset));
    }

    public static void setClassType(Class<?> clazz, Class<?> type) {
        copyInt(clazz, type, CLASS_TYPE_OFFSET);
    }

    private static final Map<Class<?>, Object> OBJECTS = new HashMap<>();

    public static void setElementType(Object object, Object type) {
        copyInt(object, type, ELEMENT_TYPE_OFFSET);
    }

    @SuppressWarnings("unchecked")
    public static <T> T castNoRestrict(Object object, T type) {
        setElementType(type, object);
        return (T) object;
    }

    @SuppressWarnings("unchecked")
    public static <T> T castNoRestrict(Object object, Class<T> type) {
        try {
            return castNoRestrict(object, (T) OBJECTS.getOrDefault(type, OBJECTS.put(type, UnsafeUtils.UNSAFE_INSTANCE.allocateInstance(type))));
        } catch (InstantiationException e) {
            throw ThrowableUtils.initCause(new ClassCastException(), e);
        }
    }

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

    public static Enum<?>[] getEnumValues(Class<?> type) throws ReflectiveOperationException {
        return ClassWrapper.wrap(type)
                .getDeclaredMethod("values")
                .setAccessible(true)
                .setType(Enum[].class)
                .invokeStatic();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> ConstructorWrapper<T> getEnumConstructor(ClassWrapper<T> wrapper, Class<?>... parameterTypes) throws ReflectiveOperationException {
        List<Class<?>> list = new ArrayList<>();
        list.add(String.class);
        list.add(int.class);
        list.addAll(Arrays.asList(parameterTypes));
        return (ConstructorWrapper<T>) wrapper.getDeclaredConstructorNoRestrict(list.toArray(Class[]::new)).setAccessibleNoRestrict(true);
    }

    public static <T extends Enum<T>> T newEnumInstance(ConstructorWrapper<T> wrapper, String name, Object... arguments) throws ReflectiveOperationException {
        List<Object> list = new ArrayList<>();
        list.add(name);
        list.add(getEnumValues(wrapper.getObject().getDeclaringClass()).length);
        list.addAll(Arrays.asList(arguments));
        return wrapper.newInstanceNoRestrict(list.toArray());
    }

    public static boolean isMethodHandleClass(Class<?> clazz) throws ReflectiveOperationException {
        return (boolean) STACK_STREAM_FACTORY_IS_METHOD_HANDLE_FRAME_METHOD.invoke(null, clazz);
    }

    public static boolean isReflectionClass(Class<?> clazz) throws ReflectiveOperationException {
        return (boolean) STACK_STREAM_FACTORY_IS_REFLECTION_FRAME_METHOD.invoke(null, clazz);
    }

    private static Class<?> findCallerClass(Class<?>[] classes, int depth) {
        return (depth > 0 && depth < classes.length) ? classes[depth] : null;
    }

    @Nullable
    public static Class<?> getCallerClass(int depth) throws ReflectiveOperationException {
        return findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()), depth);
    }

    public static Class<?> getCallerClassOrThrow(int depth) throws ReflectiveOperationException {
        Class<?> clazz = findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()), depth);
        if (clazz != null) return clazz;
        else throw new ClassNotFoundException();
    }

    private static Class<?> findCallerClass(Class<?>[] classes) throws ReflectiveOperationException {
        for (Class<?> clazz : classes) {
            if (!(clazz.isAnnotationPresent(CallerSensitive.class) ||
                    AccessibleObjectWrapper.class.isAssignableFrom(clazz) ||
                    isMethodHandleClass(clazz) ||
                    isReflectionClass(clazz))) {
                return clazz;
            }
        }
        return null;
    }

    @Nullable
    public static Class<?> getCallerClass() throws ReflectiveOperationException {
        return findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()));
    }

    public static Class<?> getCallerClassOrThrow() throws ReflectiveOperationException {
        Class<?> clazz = findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()));
        if (clazz != null) return clazz;
        else throw new ClassNotFoundException();
    }
}