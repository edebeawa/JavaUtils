#include <cstdlib>
#include "../include/jni.h"
#include "pers_edebe_util_jni_NativeReflectionUtils.h"
#include "pers_edebe_util_jni_EdebeUtilsNative.h"

JNIEXPORT jclass JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_defineClass(JNIEnv *env, jclass thisClass, jstring name, jobject loader, jbyteArray data, jint offset, jint length) {
    auto *buf = (jbyte *)malloc(length);
    if (buf == nullptr) {
        ThrowNewOutOfMemoryError(env, JNI_FALSE);
        return nullptr;
    }
    jclass clazz = nullptr;
    env->GetByteArrayRegion(data, offset, length, buf);
    if (env->ExceptionOccurred() == nullptr) {
        const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
        clazz = env->DefineClass(namePtr, loader, buf, env->GetArrayLength(data));
        env->ReleaseStringUTFChars(name, namePtr);
    }
    free(buf);
    return clazz;
}

JNIEXPORT jclass JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_findClass(JNIEnv *env, jclass thisClass, jstring name) {
    const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
    jclass clazz = env->FindClass(namePtr);
    env->ReleaseStringUTFChars(name, namePtr);
    return env->ExceptionOccurred() == nullptr ? clazz : nullptr;
}

jfieldID GetFieldID(JNIEnv *env, jclass clazz, jstring name, jstring signature) {
    const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
    const char* signaturePtr = env->GetStringUTFChars(signature, JNI_FALSE);
    jfieldID id = env->GetFieldID(clazz, namePtr, signaturePtr);
    env->ReleaseStringUTFChars(name, namePtr);
    env->ReleaseStringUTFChars(signature, signaturePtr);
    return env->ExceptionOccurred() == nullptr ? id : nullptr;
}

JNIEXPORT jobject JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getObjectField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? nullptr : env->GetObjectField(object, id);
}

JNIEXPORT jboolean JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getBooleanField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jboolean) -1 : env->GetBooleanField(object, id);
}

JNIEXPORT jbyte JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getByteField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jbyte) -1 : env->GetByteField(object, id);
}

JNIEXPORT jchar JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getCharField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jchar) -1 : env->GetCharField(object, id);
}

JNIEXPORT jshort JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getShortField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jshort) -1 : env->GetShortField(object, id);
}

JNIEXPORT jint JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getIntField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jint) -1 : env->GetIntField(object, id);
}

JNIEXPORT jlong JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getLongField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jlong) -1 : env->GetLongField(object, id);
}

JNIEXPORT jfloat JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getFloatField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jfloat) -1 : env->GetFloatField(object, id);
}

JNIEXPORT jdouble JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getDoubleField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    return id == nullptr ? (jdouble) -1 : env->GetDoubleField(object, id);
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setObjectField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jobject value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetObjectField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setBooleanField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jboolean value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetBooleanField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setByteField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jbyte value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetByteField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setCharField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jchar value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetCharField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setShortField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jshort value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetShortField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setIntField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jint value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetIntField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setLongField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jlong value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetLongField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setFloatField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jfloat value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetFloatField(object, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setDoubleField(JNIEnv *env, jclass thisClass, jclass clazz, jobject object, jstring name, jstring signature, jdouble value) {
    jfieldID id = GetFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetDoubleField(object, id, value);
    }
}

jfieldID GetStaticFieldID(JNIEnv *env, jclass clazz, jstring name, jstring signature) {
    const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
    const char* signaturePtr = env->GetStringUTFChars(signature, JNI_FALSE);
    jfieldID id = env->GetStaticFieldID(clazz, namePtr, signaturePtr);
    env->ReleaseStringUTFChars(name, namePtr);
    env->ReleaseStringUTFChars(signature, signaturePtr);
    return env->ExceptionOccurred() == nullptr ? id : nullptr;
}

JNIEXPORT jobject JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticObjectField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? nullptr : env->GetStaticObjectField(clazz, id);
}

JNIEXPORT jboolean JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticBooleanField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jboolean) -1 : env->GetStaticBooleanField(clazz, id);
}

JNIEXPORT jbyte JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticByteField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jbyte) -1 : env->GetStaticByteField(clazz, id);
}

JNIEXPORT jchar JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticCharField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jchar) -1 : env->GetStaticCharField(clazz, id);
}

JNIEXPORT jshort JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticShortField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jshort) -1 : env->GetStaticShortField(clazz, id);
}

JNIEXPORT jint JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticIntField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jint) -1 : env->GetStaticIntField(clazz, id);
}

JNIEXPORT jlong JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticLongField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jlong) -1 : env->GetStaticLongField(clazz, id);
}

JNIEXPORT jfloat JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticFloatField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jfloat) -1 : env->GetStaticFloatField(clazz, id);
}

JNIEXPORT jdouble JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_getStaticDoubleField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    return id == nullptr ? (jdouble) -1 : env->GetStaticDoubleField(clazz, id);
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticObjectField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jobject value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticObjectField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticBooleanField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jboolean value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticBooleanField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticByteField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jbyte value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticByteField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticCharField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jchar value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticCharField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticShortField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jshort value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticShortField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticIntField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jint value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticIntField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticLongField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jlong value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticLongField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticFloatField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jfloat value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticFloatField(clazz, id, value);
    }
}

JNIEXPORT void JNICALL Java_pers_edebe_util_jni_NativeReflectionUtils_setStaticDoubleField(JNIEnv *env, jclass thisClass, jclass clazz, jstring name, jstring signature, jdouble value) {
    jfieldID id = GetStaticFieldID(env, clazz, name, signature);
    if (id != nullptr) {
        env->SetStaticDoubleField(clazz, id, value);
    }
}

jmethodID GetMethodID(JNIEnv *env, jclass clazz, jstring name, jstring signature) {
    const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
    const char* signaturePtr = env->GetStringUTFChars(signature, JNI_FALSE);
    jmethodID id = env->GetMethodID(clazz, namePtr, signaturePtr);
    env->ReleaseStringUTFChars(name, namePtr);
    env->ReleaseStringUTFChars(signature, signaturePtr);
    return env->ExceptionOccurred() == nullptr ? id : nullptr;
}

jmethodID GetStaticMethodID(JNIEnv *env, jclass clazz, jstring name, jstring signature) {
    const char* namePtr = env->GetStringUTFChars(name, JNI_FALSE);
    const char* signaturePtr = env->GetStringUTFChars(signature, JNI_FALSE);
    jmethodID id = env->GetStaticMethodID(clazz, namePtr, signaturePtr);
    env->ReleaseStringUTFChars(name, namePtr);
    env->ReleaseStringUTFChars(signature, signaturePtr);
    return env->ExceptionOccurred() == nullptr ? id : nullptr;
}