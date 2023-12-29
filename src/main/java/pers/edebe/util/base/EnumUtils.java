package pers.edebe.util.base;

import org.jetbrains.annotations.Nullable;
import pers.edebe.util.reflect.ReflectionUtils;
import pers.edebe.util.wrapper.ClassWrapper;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils {
    public static Enum<?>[] getValues(Class<?> type) throws ReflectiveOperationException {
        return ClassWrapper.wrap(type)
                .getDeclaredField("$VALUES")
                .setAccessible(true)
                .setType(Enum[].class)
                .getStatic();
    }

    public static <T extends Enum<T>> T newLocalEnum(Class<T> type, String name, Object... arguments) throws ReflectiveOperationException {
        return ReflectionUtils.newEnum(type, name, getValues(type).length, arguments);
    }

    private static final Map<Class<? extends Enum<?>>, Map<String, Enum<?>>> ENUMS = new HashMap<>();

    public static <T extends Enum<T>> Map<String, Enum<?>> getGlobalEnumMap(Class<T> type) {
        return ENUMS.getOrDefault(type, ENUMS.put(type, new HashMap<>()));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getGlobalEnum(Class<T> type, String name) {
        return (T) getGlobalEnumMap(type).getOrDefault(name, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T newGlobalEnum(Class<T> type, String name, Object... arguments) throws ReflectiveOperationException {
        Map<String, Enum<?>> map = getGlobalEnumMap(type);
        return (T) map.getOrDefault(name, map.put(name, ReflectionUtils.newEnum(type, name, getValues(type).length + map.size(), arguments)));
    }
}