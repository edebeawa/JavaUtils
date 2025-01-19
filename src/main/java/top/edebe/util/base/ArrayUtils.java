package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@UtilityClass
public class ArrayUtils {
    public static boolean isArray(final @NotNull Object object) {
        return object.getClass().isArray();
    }

    public static @Nullable Object @NotNull [] toArray(final @NotNull Object object) {
        final int length = Array.getLength(object);
        final Object[] objects = new Object[length];
        for(int i = 0; i < length; i++)
            objects[i] = Array.get(object, i);
        return objects;
    }

    private static List<Object> createList(final Object[] array) {
        return Arrays.stream(array).collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull String toString(final @Nullable Object @NotNull [] array) {
        final Stack<BiObject<List<Object>, BiObject<List<Object>, Integer>>> stack = new Stack<>();
        stack.push(new BiObject<>(ArrayUtils.createList(array), null));
        String returnValue = StringUtils.NULL;
        while (!stack.isEmpty()) {
            final BiObject<List<Object>, BiObject<List<Object>, Integer>> info = stack.pop();
            final List<Object> elements = info.getLeft();
            final List<BiObject<List<Object>, BiObject<List<Object>, Integer>>> list = new ArrayList<>();
            if (elements.isEmpty()) {
                returnValue = "[]";
            }
            else {
                final StringBuilder builder = new StringBuilder();
                builder.append('[');
                for (int i = 0; ; i++) {
                    final Object element = elements.get(i);
                    if (element == null)
                        builder.append(StringUtils.NULL);
                    else if (ArrayUtils.isArray(element))
                        list.add(new BiObject<>(ArrayUtils.createList(ArrayUtils.toArray(element)), new BiObject<>(elements, i)));
                    else
                        builder.append(element);
                    if (i != elements.size() - 1) {
                        builder.append(", ");
                    }
                    else {
                        builder.append(']');
                        returnValue = builder.toString();
                        break;
                    }
                }
            }
            if (list.isEmpty()) {
                final BiObject<List<Object>, Integer> object = info.getRight();
                if (object != null)
                    object.getLeft().set(object.getRight(), returnValue);
            }
            else {
                list.add(0, info);
                list.forEach(stack::push);
            }
        }
        return returnValue;
    }

    public static boolean equals(final @Nullable Object @Nullable [] array0, final @Nullable Object @Nullable [] array1) {
        if (array0 == null) {
            return array1 == null || array1.length == 0;
        }
        else if (array1 == null) {
            return array0.length == 0;
        }
        else if (array0.length != array1.length) {
            return false;
        }
        else {
            for (int i = 0; i < array0.length; i++) {
                final Object element0 = array0[i];
                final Object element1 = array1[i];
                if ((element0 == null && element1 != null) ||
                        (element0 != null && element1 == null) ||
                        (element0 != null && !element0.equals(element1)))
                    return false;
            }
            return true;
        }
    }
}