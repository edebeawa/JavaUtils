package top.edebe.util.reflect;

import top.edebe.util.jni.NativeReflectionUtils;
import top.edebe.util.misc.UnsafeUtils;
import top.edebe.util.wrapper.AccessibleObjectWrapper;
import top.edebe.util.wrapper.ClassWrapper;
import top.edebe.util.wrapper.ConstructorWrapper;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
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
            CLASS_FOR_NAME_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "forName0", String.class, boolean.class, ClassLoader.class, Class.class);
            SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(SecurityManager.class, "getClassContext");
            final Class<?> classStackStreamFactory = Class.forName("java.lang.StackStreamFactory");
            STACK_STREAM_FACTORY_IS_METHOD_HANDLE_FRAME_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(classStackStreamFactory, "isMethodHandleFrame", Class.class);
            STACK_STREAM_FACTORY_IS_REFLECTION_FRAME_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(classStackStreamFactory, "isReflectionFrame", Class.class);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyInt(final @NotNull Object object0, final @NotNull Object object1, final long offset) {
        UnsafeUtils.UNSAFE_INSTANCE.putInt(object0, offset, UnsafeUtils.UNSAFE_INSTANCE.getInt(object1, offset));
    }

    public static void setClassType(final @NotNull Class<?> clazz, final @NotNull Class<?> type) {
        ReflectionUtils.copyInt(clazz, type, CLASS_TYPE_OFFSET);
    }

    private static final Map<Class<?>, Object> OBJECTS = new HashMap<>();

    public static void setElementType(final @NotNull Object object, final @NotNull Object type) {
        ReflectionUtils.copyInt(object, type, ELEMENT_TYPE_OFFSET);
    }

    @SuppressWarnings("unchecked")
    public static @NotNull <T> T castNoRestrict(final @NotNull Object object, final @NotNull T type) {
        ReflectionUtils.setElementType(type, object);
        return (T) object;
    }

    @SuppressWarnings("unchecked")
    public static @NotNull <T> T castNoRestrict(final @NotNull Object object, final @NotNull Class<T> type) {
        try {
            return ReflectionUtils.castNoRestrict(object, (T) OBJECTS.getOrDefault(type, OBJECTS.put(type, UnsafeUtils.UNSAFE_INSTANCE.allocateInstance(type))));
        }
        catch (InstantiationException e) {
            throw (ClassCastException) new ClassCastException().initCause(e);
        }
    }

    public static void setAccessibleNoRestrict(final @NotNull AccessibleObject object, final boolean flag) {
        UnsafeUtils.UNSAFE_INSTANCE.putBoolean(object, UnsafeUtils.ACCESS_MODIFIER_OFFSET, flag);
    }

    public static @NotNull Field getAccessibleField(final @NotNull Class<?> clazz, final @NotNull String name) throws NoSuchFieldException {
        final Field field = clazz.getField(name);
        ReflectionUtils.setAccessibleNoRestrict(field, true);
        return field;
    }

    public static @NotNull Method getAccessibleMethod(final @NotNull Class<?> clazz, final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws NoSuchMethodException {
        final Method method = clazz.getMethod(name, parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(method, true);
        return method;
    }

    public static @NotNull <T> Constructor<T> getAccessibleConstructor(final @NotNull Class<T> clazz, final @NotNull Class<?> @NotNull ... parameterTypes) throws NoSuchMethodException {
        final Constructor<T> constructor = clazz.getConstructor(parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(constructor, true);
        return constructor;
    }

    public static @NotNull Field getAccessibleDeclaredField(final @NotNull Class<?> clazz, final @NotNull String name) throws NoSuchFieldException {
        final Field field = clazz.getDeclaredField(name);
        ReflectionUtils.setAccessibleNoRestrict(field, true);
        return field;
    }

    public static @NotNull Method getAccessibleDeclaredMethod(final @NotNull Class<?> clazz, final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws NoSuchMethodException {
        final Method method = clazz.getDeclaredMethod(name, parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(method, true);
        return method;
    }

    public static @NotNull <T> Constructor<T> getAccessibleDeclaredConstructor(final @NotNull Class<T> clazz, final @NotNull Class<?> @NotNull ... parameterTypes) throws NoSuchMethodException {
        final Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
        ReflectionUtils.setAccessibleNoRestrict(constructor, true);
        return constructor;
    }

    public static @NotNull Class<?> getClassForName(final @NotNull String name, final boolean initialize, final @Nullable ClassLoader loader, final @Nullable Class<?> caller) throws InvocationTargetException, IllegalAccessException {
        return (Class<?>) CLASS_FOR_NAME_METHOD.invoke(null, name, initialize, loader, caller);
    }

    public static @NotNull Class<?> getClassForName(final @NotNull String name, final boolean initialize) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        final Class<?> caller = ReflectionUtils.getCallerClass();
        if (caller != null)
            return ReflectionUtils.getClassForName(name, initialize, caller.getClassLoader(), caller);
        else
            throw new ClassNotFoundException();
    }

    public static @NotNull Class<?> getClassForName(final @NotNull String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return ReflectionUtils.getClassForName(name, true);
    }

    public static @NotNull Enum<?> @NotNull [] getEnumValues(final @NotNull Class<?> type) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return ClassWrapper.wrap(type)
                .getDeclaredMethod("values")
                .setAccessible(true)
                .setType(Enum[].class)
                .invokeStatic();
    }

    @SuppressWarnings("unchecked")
    public static @NotNull <T extends Enum<T>> ConstructorWrapper<T> getEnumConstructor(final @NotNull ClassWrapper<T> wrapper, final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Class<?>> list = new ArrayList<>();
        list.add(String.class);
        list.add(int.class);
        list.addAll(Arrays.asList(parameterTypes));
        return (ConstructorWrapper<T>) wrapper.getDeclaredConstructorNoRestrict(list.toArray(Class[]::new)).setAccessibleNoRestrict(true);
    }

    public static @NotNull <T extends Enum<T>> T newEnumInstance(final @NotNull ConstructorWrapper<T> wrapper, final @NotNull String name, final @NotNull Object @NotNull ... arguments) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Object> list = new ArrayList<>();
        list.add(name);
        list.add(ReflectionUtils.getEnumValues(wrapper.getObject().getDeclaringClass()).length);
        list.addAll(Arrays.asList(arguments));
        return wrapper.newInstanceNoRestrict(list.toArray());
    }

    public static Method toMethodObject(long id) {
        return NativeReflectionUtils.toMethodObject(NativeReflectionUtils.getMethodDeclaringClass(id), id, Modifier.isStatic(NativeReflectionUtils.getMethodModifiers(id)));
    }

    public static boolean isMethodHandleClass(final @NotNull Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        return (boolean) STACK_STREAM_FACTORY_IS_METHOD_HANDLE_FRAME_METHOD.invoke(null, clazz);
    }

    public static boolean isReflectionClass(final @NotNull Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        return (boolean) STACK_STREAM_FACTORY_IS_REFLECTION_FRAME_METHOD.invoke(null, clazz);
    }

    private static @Nullable Class<?> findCallerClass(final @Nullable Class<?> @NotNull [] classes, final int depth) {
        return (depth > 0 && depth < classes.length) ? classes[depth] : null;
    }

    public static @Nullable Class<?> getCallerClass(final int depth) throws InvocationTargetException, IllegalAccessException {
        return ReflectionUtils.findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()), depth);
    }

    public static @NotNull Class<?> getCallerClassOrThrow(final int depth) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = ReflectionUtils.findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()), depth);
        if (clazz != null) return clazz;
        else throw new ClassNotFoundException();
    }

    private static @Nullable Class<?> findCallerClass(final @NotNull Class<?> @NotNull [] classes) throws InvocationTargetException, IllegalAccessException {
        for (final Class<?> clazz : classes) {
            if (!(clazz.isAnnotationPresent(CallerSensitive.class) ||
                    AccessibleObjectWrapper.class.isAssignableFrom(clazz) ||
                    ReflectionUtils.isMethodHandleClass(clazz) ||
                    ReflectionUtils.isReflectionClass(clazz))) {
                return clazz;
            }
        }
        return null;
    }

    public static @Nullable Class<?> getCallerClass() throws InvocationTargetException, IllegalAccessException {
        return ReflectionUtils.findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()));
    }

    public static @NotNull Class<?> getCallerClassOrThrow() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        final Class<?> clazz = ReflectionUtils.findCallerClass((Class<?>[]) SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD.invoke(new SecurityManager()));
        if (clazz != null) return clazz;
        else throw new ClassNotFoundException();
    }
}