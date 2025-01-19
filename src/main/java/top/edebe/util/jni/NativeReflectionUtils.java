package top.edebe.util.jni;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class NativeReflectionUtils extends JavaUtilsNative {
    static {
        NativeReflectionUtils.registerNatives();
    }

    private static native int registerNatives();

    public static native Class<?> defineClass(String name, ClassLoader loader, byte[] data, int offset, int length);

    public static native Class<?> findClass(String name);

    public static native Object allocObject(Class<?> clazz);

    public static native long toFieldId(Field field);

    public static native Field toFieldObject(Class<?> clazz, long id, boolean isStatic);

    public static native long getFieldId(Class<?> clazz, String name, String signature);

    public static native Object getObjectField(Object object, long id);

    public static native boolean getBooleanField(Object object, long id);

    public static native byte getByteField(Object object, long id);

    public static native char getCharField(Object object, long id);

    public static native short getShortField(Object object, long id);

    public static native int getIntField(Object object, long id);

    public static native long getLongField(Object object, long id);

    public static native float getFloatField(Object object, long id);

    public static native double getDoubleField(Object object, long id);

    public static native void setObjectField(Object object, long id, Object value);

    public static native void setBooleanField(Object object, long id, boolean value);

    public static native void setByteField(Object object, long id, byte value);

    public static native void setCharField(Object object, long id, char value);

    public static native void setShortField(Object object, long id, short value);

    public static native void setIntField(Object object, long id, int value);

    public static native void setLongField(Object object, long id, long value);

    public static native void setFloatField(Object object, long id, float value);

    public static native void setDoubleField(Object object, long id, double value);

    public static native long getStaticFieldId(Class<?> clazz, String name, String signature);

    public static native Object getStaticObjectField(Class<?> clazz, long id);

    public static native boolean getStaticBooleanField(Class<?> clazz, long id);

    public static native byte getStaticByteField(Class<?> clazz, long id);

    public static native char getStaticCharField(Class<?> clazz, long id);

    public static native short getStaticShortField(Class<?> clazz, long id);

    public static native int getStaticIntField(Class<?> clazz, long id);

    public static native long getStaticLongField(Class<?> clazz, long id);

    public static native float getStaticFloatField(Class<?> clazz, long id);

    public static native double getStaticDoubleField(Class<?> clazz, long id);

    public static native void setStaticObjectField(Class<?> clazz, long id, Object value);

    public static native void setStaticBooleanField(Class<?> clazz, long id, boolean value);

    public static native void setStaticByteField(Class<?> clazz, long id, byte value);

    public static native void setStaticCharField(Class<?> clazz, long id, char value);

    public static native void setStaticShortField(Class<?> clazz, long id, short value);

    public static native void setStaticIntField(Class<?> clazz, long id, int value);

    public static native void setStaticLongField(Class<?> clazz, long id, long value);

    public static native void setStaticFloatField(Class<?> clazz, long id, float value);

    public static native void setStaticDoubleField(Class<?> clazz, long id, double value);

    public static native long[] getStackTrace(Thread thread, int startDepth, int maxFrameCount);

    public static native String[] getMethodName(long id);

    public static native Class<?> getMethodDeclaringClass(long id);

    public static native int getMethodModifiers(long id);

    public static native long toMethodId(Method method);

    public static native Method toMethodObject(Class<?> clazz, long id, boolean isStatic);

    public static native long getMethodId(Class<?> clazz, String name, String signature);

    public static native void callVoidMethod(Object object, long id, Object... args);

    public static native Object callObjectMethod(Object object, long id, Object... args);

    public static native boolean callBooleanMethod(Object object, long id, Object... args);

    public static native byte callByteMethod(Object object, long id, Object... args);

    public static native char callCharMethod(Object object, long id, Object... args);

    public static native short callShortMethod(Object object, long id, Object... args);

    public static native int callIntMethod(Object object, long id, Object... args);

    public static native long callLongMethod(Object object, long id, Object... args);

    public static native float callFloatMethod(Object object, long id, Object... args);

    public static native double callDoubleMethod(Object object, long id, Object... args);

    public static native void callNonvirtualVoidMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native Object callNonvirtualObjectMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native boolean callNonvirtualBooleanMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native byte callNonvirtualByteMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native char callNonvirtualCharMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native short callNonvirtualShortMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native int callNonvirtualIntMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native long callNonvirtualLongMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native float callNonvirtualFloatMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native double callNonvirtualDoubleMethod(Object object, Class<?> clazz, long id, Object... args);

    public static native long getStaticMethodId(Class<?> clazz, String name, String signature);

    public static native void callStaticVoidMethod(Class<?> clazz, long id, Object... args);

    public static native Object callStaticObjectMethod(Class<?> clazz, long id, Object... args);

    public static native boolean callStaticBooleanMethod(Class<?> clazz, long id, Object... args);

    public static native byte callStaticByteMethod(Class<?> clazz, long id, Object... args);

    public static native char callStaticCharMethod(Class<?> clazz, long id, Object... args);

    public static native short callStaticShortMethod(Class<?> clazz, long id, Object... args);

    public static native int callStaticIntMethod(Class<?> clazz, long id, Object... args);

    public static native long callStaticLongMethod(Class<?> clazz, long id, Object... args);

    public static native float callStaticFloatMethod(Class<?> clazz, long id, Object... args);

    public static native double callStaticDoubleMethod(Class<?> clazz, long id, Object... args);
}