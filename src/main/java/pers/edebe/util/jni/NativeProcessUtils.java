package pers.edebe.util.jni;

public class NativeProcessUtils extends EdebeUtilsNative {
    public static native void writeProcessMemory(long pid, long address, long value);

    public static native long readProcessMemory(long pid, long address);
}