package pers.edebe.util.misc;

import pers.edebe.util.reflect.AccessUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public final class UnsafeUtils {
    public static final int ACCESS_MODIFIER_OFFSET;
    public static final Unsafe UNSAFE_INSTANCE;
    public static final Object INTERNAL_UNSAFE_INSTANCE;
    public static final Class<?> INTERNAL_UNSAFE_CLASS;
    private static final Method INTERNAL_UNSAFE_DEFINE_CLASS_METHOD;
    private static final Method INTERNAL_UNSAFE_GET_REFERENCE_METHOD;
    private static final Method INTERNAL_UNSAFE_PUT_REFERENCE_METHOD;

    static {
        try {
            int offset;//override boolean byte offset.
            Field field0 = Unsafe.class.getDeclaredField("theUnsafe");
            Field field1 = Unsafe.class.getDeclaredField("theUnsafe");
            field0.setAccessible(true);
            field1.setAccessible(false);
            UNSAFE_INSTANCE = (Unsafe) field0.get(null);
            //noinspection StatementWithEmptyBody
            for (offset = 0; UNSAFE_INSTANCE.getBoolean(field0, offset) == UNSAFE_INSTANCE.getBoolean(field1, offset); offset++);
            ACCESS_MODIFIER_OFFSET = offset;
            INTERNAL_UNSAFE_INSTANCE = AccessUtils.getAccessibleDeclaredField(Unsafe.class, "theInternalUnsafe").get(null);
            INTERNAL_UNSAFE_CLASS = INTERNAL_UNSAFE_INSTANCE.getClass();
            INTERNAL_UNSAFE_DEFINE_CLASS_METHOD = AccessUtils.getAccessibleDeclaredMethod(INTERNAL_UNSAFE_CLASS, "defineClass", String.class, byte[].class, int.class, int.class, ClassLoader.class, ProtectionDomain.class);
            INTERNAL_UNSAFE_GET_REFERENCE_METHOD = AccessUtils.getAccessibleDeclaredMethod(INTERNAL_UNSAFE_CLASS, "getReference", Object.class, long.class);
            INTERNAL_UNSAFE_PUT_REFERENCE_METHOD = AccessUtils.getAccessibleDeclaredMethod(INTERNAL_UNSAFE_CLASS, "putReference", Object.class, long.class, Object.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> defineClass(String name, byte[] code, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) throws ReflectiveOperationException {
        return (Class<?>) INTERNAL_UNSAFE_DEFINE_CLASS_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, name, code, off, len, loader, protectionDomain);
    }

    public static Object getReference(Object object, long offset) throws ReflectiveOperationException {
        return INTERNAL_UNSAFE_GET_REFERENCE_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, object, offset);
    }

    public static void putReference(Object object, long offset, Object x) throws ReflectiveOperationException {
        INTERNAL_UNSAFE_PUT_REFERENCE_METHOD.invoke(INTERNAL_UNSAFE_INSTANCE, object, offset, x);
    }
}