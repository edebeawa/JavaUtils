package pers.edebe.util.jni;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativeProcessUtils extends EdebeUtilsNative {
    public static native void writeProcessMemory(long pid, long address, long value);

    public static native long readProcessMemory(long pid, long address);
}