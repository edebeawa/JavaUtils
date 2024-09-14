package be.cloudns.edebe.util.jni;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativeProcessUtils extends JavaUtilsNative {
    public static native void writeProcessMemory(long pid, long address, long value);

    public static native long readProcessMemory(long pid, long address);
}