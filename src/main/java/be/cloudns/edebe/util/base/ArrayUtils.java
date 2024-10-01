package be.cloudns.edebe.util.base;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@UtilityClass
public class ArrayUtils {
    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    public static Object[] toArray(Object object) {
        int length = Array.getLength(object);
        Object[] objects = new Object[length];
        for(int i = 0; i < length; i++) {
            objects[i] = Array.get(object, i);
        }
        return objects;
    }

    public static String toString(Object[] array) {
        Stack<ArrayInfo> stack = new Stack<>();
        stack.push(new ArrayInfo(array));
        String returnValue = null;
        while (!stack.isEmpty()) {
            ArrayInfo info = stack.pop();
            List<Object> list = info.thisList();
            List<ArrayInfo> elements = new ArrayList<>();
            if (list == null) {
                returnValue = StringUtils.NULL;
            } else if (list.isEmpty()) {
                returnValue = "[]";
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append('[');
                for (int i = 0; ; i++) {
                    Object element = list.get(i);
                    if (element == null) {
                        builder.append(StringUtils.NULL);
                    } else if (isArray(element)) {
                        elements.add(new ArrayInfo(list, i, element));
                    } else {
                        builder.append(element);
                    }
                    if (i != list.size() - 1) {
                        builder.append(", ");
                    } else {
                        builder.append(']');
                        returnValue = builder.toString();
                        break;
                    }
                }
            }
            if (elements.isEmpty()) {
                info.finish(returnValue);
            } else {
                elements.add(0, info);
                elements.forEach(stack::push);
            }
        }
        return returnValue;
    }

    private record ArrayInfo(List<Object> superList, int index, List<Object> thisList) {
        public ArrayInfo(List<Object> list, int index, Object[] array) {
            this(list, index, new ArrayList<>(Arrays.stream(array).toList()));
        }

        public ArrayInfo(List<Object> list, int index, Object array) {
            this(list, index, toArray(array));
        }

        public ArrayInfo(Object[] array) {
            this(null, -1, array);
        }

        public void finish(String text) {
            if (superList != null) {
                superList.set(index, text);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] cast(Object[] objects, Class<T> type, int length) {
        T[] array = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < length; i++) array[i] = (T) objects[i];
        return array;
    }

    public static <T> T[] transform(T[] array, Consumer<List<T>> consumer) {
        List<T> list = new ArrayList<>(Arrays.stream(array).toList());
        consumer.accept(list);
        return list.toArray(array);
    }

    public static boolean equals(Object[] array0, Object[] array1) {
        if (array0 == null)
            return array1 == null || array1.length == 0;
        if (array1 == null)
            return array0.length == 0;
        if (array0.length != array1.length)
            return false;
        for (int i = 0; i < array0.length; i++) {
            if (!array0[i].equals(array1[i]))
                return false;
        }
        return true;
    }

    private static final Map<Class<?>, Class<?>> MAP = Map.of(
            Void.TYPE,
            Void.class,
            Boolean.TYPE,
            Boolean.class,
            Byte.TYPE,
            Byte.class,
            Character.TYPE,
            Character.class,
            Double.TYPE,
            Double.class,
            Float.TYPE,
            Float.class,
            Integer.TYPE,
            Integer.class,
            Long.TYPE,
            Long.class,
            Short.TYPE,
            Short.class
    );
}