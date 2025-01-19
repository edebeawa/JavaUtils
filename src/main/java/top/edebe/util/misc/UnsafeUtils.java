package top.edebe.util.misc;

import top.edebe.util.reflect.ReflectionUtils;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

@UtilityClass
public class UnsafeUtils {
    public static final int ACCESS_MODIFIER_OFFSET;
    public static final Unsafe UNSAFE_INSTANCE;
    public static final Object INTERNAL_UNSAFE_INSTANCE;
    public static final Class<?> INTERNAL_UNSAFE_CLASS;
    private static final Method INTERNAL_UNSAFE_DEFINE_CLASS_METHOD;
    private static final Method INTERNAL_UNSAFE_GET_REFERENCE_METHOD;
    private static final Method INTERNAL_UNSAFE_PUT_REFERENCE_METHOD;

    static {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE_INSTANCE = (Unsafe) field.get(null);
            final AccessibleObject object0 = new InstantiableAccessibleObject(true);
            final AccessibleObject object1 = new InstantiableAccessibleObject(false);
            int offset = 0;//override boolean byte offset.
            while (UNSAFE_INSTANCE.getBoolean(object0, offset) == UNSAFE_INSTANCE.getBoolean(object1, offset)) offset++;
            ACCESS_MODIFIER_OFFSET = offset;
            final Object internalUnsafeInstance = ReflectionUtils.getAccessibleDeclaredField(Unsafe.class, "theInternalUnsafe").get(null);
            INTERNAL_UNSAFE_INSTANCE = internalUnsafeInstance;
            final Class<?> internalUnsafeClass = internalUnsafeInstance.getClass();
            INTERNAL_UNSAFE_CLASS = internalUnsafeClass;
            INTERNAL_UNSAFE_DEFINE_CLASS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(internalUnsafeClass, "defineClass", String.class, byte[].class, int.class, int.class, ClassLoader.class, ProtectionDomain.class);
            INTERNAL_UNSAFE_GET_REFERENCE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(internalUnsafeClass, "getReference", Object.class, long.class);
            INTERNAL_UNSAFE_PUT_REFERENCE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(internalUnsafeClass, "putReference", Object.class, long.class, Object.class);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull Class<?> defineClass(final @Nullable String name, final byte @NotNull [] code, final int off, final int len, final @Nullable ClassLoader loader, final @Nullable ProtectionDomain protectionDomain) throws InvocationTargetException, IllegalAccessException {
        return (Class<?>) INTERNAL_UNSAFE_DEFINE_CLASS_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, name, code, off, len, loader, protectionDomain);
    }

    public static Object getReference(final @NotNull Object object, final long offset) throws InvocationTargetException, IllegalAccessException {
        return INTERNAL_UNSAFE_GET_REFERENCE_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, object, offset);
    }

    public static void putReference(final @NotNull Object object, final long offset, final Object x) throws InvocationTargetException, IllegalAccessException {
        INTERNAL_UNSAFE_PUT_REFERENCE_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, object, offset, x);
    }

    private static class InstantiableAccessibleObject extends AccessibleObject {
        private InstantiableAccessibleObject(boolean accessible) {
            super();
            this.setAccessible(accessible);
        }
    }
}