package top.edebe.util.misc;

import lombok.experimental.UtilityClass;
import sun.misc.Unsafe;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

@UtilityClass
public class UnsafeUtils {
    public static final long ACCESS_MODIFIER_OFFSET;
    public static final Unsafe UNSAFE_INSTANCE;

    static {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE_INSTANCE = (Unsafe) field.get(null);
            final AccessibleObject object0 = new InstantiableAccessibleObject(true);
            final AccessibleObject object1 = new InstantiableAccessibleObject(false);
            long offset = 0;//override boolean byte offset.
            while (UNSAFE_INSTANCE.getBoolean(object0, offset) == UNSAFE_INSTANCE.getBoolean(object1, offset)) offset++;
            ACCESS_MODIFIER_OFFSET = offset;
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class InstantiableAccessibleObject extends AccessibleObject {
        private InstantiableAccessibleObject(boolean accessible) {
            super();
            this.setAccessible(accessible);
        }
    }
}