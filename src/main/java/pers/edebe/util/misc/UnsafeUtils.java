package pers.edebe.util.misc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class UnsafeUtils {
    public static final long ACCESS_MODIFIER_OFFSET;
    public static final Unsafe UNSAFE_INSTANCE;

    static {
        try {
            Field field0 = Unsafe.class.getDeclaredField("theUnsafe");
            Field field1 = Unsafe.class.getDeclaredField("theUnsafe");
            field0.setAccessible(true);
            field1.setAccessible(false);
            UNSAFE_INSTANCE = (Unsafe) field0.get(null);
            long offset = 0;//override boolean byte offset.
            while (UNSAFE_INSTANCE.getBoolean(field0, offset) == UNSAFE_INSTANCE.getBoolean(field1, offset)) offset++;
            ACCESS_MODIFIER_OFFSET = offset;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}