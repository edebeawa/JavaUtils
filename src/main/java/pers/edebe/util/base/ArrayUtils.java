package pers.edebe.util.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ArrayUtils {
    public static Object[] toArray(Object object) {
        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            Object[] objects = new Object[length];
            for(int i = 0; i < length; i++) {
                objects[i] = Array.get(object, i);
            }
            return objects;
        }
        return new Object[0];
    }

    public static String toString(Object[] array) {
        Stack<ArrayInfo> stack = new Stack<>();
        stack.push(new ArrayInfo(array));
        String returnValue = null;
        while (!stack.isEmpty()) {
            ArrayInfo info = stack.pop();
            List<Object> list = info.getThisList();
            List<ArrayInfo> elements = new ArrayList<>();
            if (list == null) {
                returnValue = "null";
            } else if (list.isEmpty()) {
                returnValue = "[]";
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append('[');
                for (int i = 0; ; i++) {
                    Object element = list.get(i);
                    if (element == null) {
                        builder.append("null");
                    } else if (element.getClass().isArray()) {
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

    @Getter
    @AllArgsConstructor
    private static class ArrayInfo {
        private final List<Object> superList;
        private final int index;
        private final List<Object> thisList;

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

    public static <T> T[] transform(T[] array, Consumer<List<T>> consumer) {
        List<T> list = new ArrayList<>(Arrays.stream(array).toList());
        consumer.accept(list);
        return list.toArray(array);
    }

    private static <T> boolean equals(T[] array0, T[] array1, BiFunction<T, T, Boolean> function) {
        if (array0 == null) {
            return array1 == null || array1.length == 0;
        }

        if (array1 == null) {
            return array0.length == 0;
        }

        if (array0.length != array1.length) {
            return false;
        }

        for (int i = 0; i < array0.length; i++) {
            if (!function.apply(array0[i], array1[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(Object[] array0, Object[] array1) {
        return equals(array0, array1, Object::equals);
    }

    private static boolean equals(Class<?> class0, Class<?> class1, boolean fuzzy) {
        Class<?> c0 = class0;
        Class<?> c1 = class1;
        if (fuzzy) {
            if (TypeMap.isPrimitiveClass(c0)) {
                c0 = TypeMap.getWrapClass(c0);
            }
            if (TypeMap.isPrimitiveClass(c1)) {
                c1 = TypeMap.getWrapClass(c1);
            }
        }
        return c0.equals(c1);
    }

    public static boolean equals(Class<?>[] array0, Class<?>[] array1, boolean fuzzy) {
        return ArrayUtils.equals(array0, array1, (class0, class1) -> equals(class0, class1, fuzzy));
    }
}