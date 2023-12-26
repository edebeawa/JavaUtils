package pers.edebe.util.jni;

public class NativeReflectionUtils extends EdebeUtilsNative {
    public static native Object getObjectField(Class<?> clazz, Object object, String name, String signature);

    public static native boolean getBooleanField(Class<?> clazz, Object object, String name, String signature);

    public static native byte getByteField(Class<?> clazz, Object object, String name, String signature);

    public static native char getCharField(Class<?> clazz, Object object, String name, String signature);

    public static native short getShortField(Class<?> clazz, Object object, String name, String signature);

    public static native int getIntField(Class<?> clazz, Object object, String name, String signature);

    public static native long getLongField(Class<?> clazz, Object object, String name, String signature);

    public static native float getFloatField(Class<?> clazz, Object object, String name, String signature);

    public static native double getDoubleField(Class<?> clazz, Object object, String name, String signature);

    public static native void setObjectField(Class<?> clazz, Object object, String name, String signature, Object value);

    public static native void setBooleanField(Class<?> clazz, Object object, String name, String signature, boolean value);

    public static native void setByteField(Class<?> clazz, Object object, String name, String signature, byte value);

    public static native void setCharField(Class<?> clazz, Object object, String name, String signature, char value);

    public static native void setShortField(Class<?> clazz, Object object, String name, String signature, short value);

    public static native void setIntField(Class<?> clazz, Object object, String name, String signature, int value);

    public static native void setLongField(Class<?> clazz, Object object, String name, String signature, long value);

    public static native void setFloatField(Class<?> clazz, Object object, String name, String signature, float value);

    public static native void setDoubleField(Class<?> clazz, Object object, String name, String signature, double value);

    public static native Object getStaticObjectField(Class<?> clazz, String name, String signature);

    public static native boolean getStaticBooleanField(Class<?> clazz, String name, String signature);

    public static native byte getStaticByteField(Class<?> clazz, String name, String signature);

    public static native char getStaticCharField(Class<?> clazz, String name, String signature);

    public static native short getStaticShortField(Class<?> clazz, String name, String signature);

    public static native int getStaticIntField(Class<?> clazz, String name, String signature);

    public static native long getStaticLongField(Class<?> clazz, String name, String signature);

    public static native float getStaticFloatField(Class<?> clazz, String name, String signature);

    public static native double getStaticDoubleField(Class<?> clazz, String name, String signature);

    public static native void setStaticObjectField(Class<?> clazz, String name, String signature, Object value);

    public static native void setStaticBooleanField(Class<?> clazz, String name, String signature, boolean value);

    public static native void setStaticByteField(Class<?> clazz, String name, String signature, byte value);

    public static native void setStaticCharField(Class<?> clazz, String name, String signature, char value);

    public static native void setStaticShortField(Class<?> clazz, String name, String signature, short value);

    public static native void setStaticIntField(Class<?> clazz, String name, String signature, int value);

    public static native void setStaticLongField(Class<?> clazz, String name, String signature, long value);

    public static native void setStaticFloatField(Class<?> clazz, String name, String signature, float value);

    public static native void setStaticDoubleField(Class<?> clazz, String name, String signature, double value);
}