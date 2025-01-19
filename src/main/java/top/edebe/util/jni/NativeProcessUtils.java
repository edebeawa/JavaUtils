package top.edebe.util.jni;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NativeProcessUtils extends JavaUtilsNative {
    static {
        NativeProcessUtils.registerNatives();
    }

    private static native int registerNatives();

    public static native void writeProcessMemory(long pid, long address, long value);

    public static native long readProcessMemory(long pid, long address);
}