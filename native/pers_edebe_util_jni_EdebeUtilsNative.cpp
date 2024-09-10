#include "../include/jni.h"
#include "pers_edebe_util_jni_EdebeUtilsNative.h"

const char *NAMES[] = {"java/lang/OutOfMemoryError"};
jclass CLASSES[sizeof(NAMES) / sizeof(NAMES[0])];

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_EdebeUtilsNative_initialize(JNIEnv *env, jclass thisClass) {
    for (int i = 0; i < sizeof(NAMES) / sizeof(NAMES[0]); i++) {
        jclass clazz = env->FindClass(NAMES[i]);
        CLASSES[i] = (jclass)env->NewGlobalRef(clazz);
        env->DeleteLocalRef(clazz);
    }
}

jint ThrowNewOutOfMemoryError(JNIEnv *env, const char *msg) {
    return env->ThrowNew(CLASSES[0], msg);
}