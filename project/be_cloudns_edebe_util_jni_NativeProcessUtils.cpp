#include "../include/jni.h"
#include "../include/jvm.h"
#include "be_cloudns_edebe_util_jni_NativeProcessUtils.h"

JNIEXPORT jlong JNICALL Java_be_cloudns_edebe_util_jni_NativeProcessUtils_readProcessMemory(JNIEnv *env, jclass thisClass, jlong pid, jlong address) {
    HANDLE handle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pid);
    jlong value;
    ReadProcessMemory(handle, (PBYTE*) address, &value, sizeof(value), nullptr);
    return value;
}

JNIEXPORT void JNICALL Java_be_cloudns_edebe_util_jni_NativeProcessUtils_writeProcessMemory(JNIEnv *env, jclass thisClass, jlong pid, jlong address, jlong value) {
    HANDLE handle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pid);
    WriteProcessMemory(handle, (PBYTE*) address, &value, sizeof(value), nullptr);
}