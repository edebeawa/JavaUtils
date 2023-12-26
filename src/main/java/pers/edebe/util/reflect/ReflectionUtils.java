package pers.edebe.util.reflect;

import org.jetbrains.annotations.Nullable;
import pers.edebe.util.base.ClassUtils;
import pers.edebe.util.misc.UnsafeUtils;
import pers.edebe.util.wrapper.AccessibleObjectWrapper;
import pers.edebe.util.wrapper.ClassWrapper;

import java.lang.reflect.AccessibleObject;
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
            REFLECTION_FACTORY_INSTANCE = AccessUtils.getAccessibleDeclaredMethod(Class.class, "getReflectionFactory").invoke(null);
            REFLECTION_FACTORY_CLASS = REFLECTION_FACTORY_INSTANCE.getClass();
            CLASS_FOR_NAME_METHOD = AccessUtils.getAccessibleDeclaredMethod(Class.class, "forName0", String.class, boolean.class, ClassLoader.class, Class.class);
            SECURITY_MANAGER_GET_CLASS_CONTEXT_METHOD = AccessUtils.getAccessibleDeclaredMethod(SecurityManager.class, "getClassContext");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    //write directly into override to bypass perms
    public static void setAccessibleNoRestrict(AccessibleObject object, boolean flag) {
        UnsafeUtils.UNSAFE_INSTANCE.putBoolean(object, UnsafeUtils.ACCESS_MODIFIER_OFFSET, flag);
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

    private static final Map<Class<? extends Enum<?>>, Map<String, Enum<?>>> ENUMS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T newEnum(Class<T> type, String name, Object... arguments) throws ReflectiveOperationException {
        Map<String, Enum<?>> map;
        if (ENUMS.containsKey(type)) {
            map = ENUMS.get(type);
        } else {
            map = new HashMap<>();
            Arrays.stream(ClassWrapper.wrap(type)
                    .getDeclaredMethodExactMatch("values")
                    .setType(Enum[].class)
                    .invokeStatic()).forEach((value) -> map.put(value.name(), value));
            ENUMS.put(type, map);
        }

        if (map.containsKey(name)) {
            return (T) map.get(name);
        } else {
            List<Object> list = new ArrayList<>();
            list.add(name);
            list.add(map.size());
            list.addAll(Arrays.asList(arguments));
            Object[] objects = list.toArray();
            T value = ClassWrapper.wrap(type)
                    .getDeclaredConstructorNoRestrictFuzzyMatch(ClassUtils.getClass(objects))
                    .setAccessibleNoRestrict(true)
                    .setType(type)
                    .newInstanceNoRestrict(objects);
            map.put(name, value);
            return value;
        }
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