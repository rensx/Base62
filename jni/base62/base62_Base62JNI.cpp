// Save as "Base62JNI.c"
#include <jni.h>        // JNI header provided by JDK
#include <string>
#include <iostream>
#include "base62_Base62JNI.h"   // Generated

std::string encode(int64_t n, int b);
std::string encodeI(int32_t n, int b);

std::string encodeD(double n, int b);
std::string encodeF(float n, int b);
int64_t decode(const std::string &s, int b);
double decodeD(const std::string &s, int b);
float decodeF(const std::string &s, int b);
// Implementation of the native method sayHello()
JNIEXPORT jstring JNICALL Java_base62_Base62JNI_encode__JI(JNIEnv *env, jobject obj, jlong n, jint b) {

   //std::cout << encode(n, b) << std::endl;
   return env->NewStringUTF(encode(n, b).c_str());

}
JNIEXPORT jstring JNICALL Java_base62_Base62JNI_encode__II(JNIEnv *env, jobject obj, jint n, jint b) {

   //std::cout << encode(n, b) << std::endl;
   return env->NewStringUTF(encodeI(n, b).c_str());

}
JNIEXPORT jstring JNICALL Java_base62_Base62JNI_encode__DI(JNIEnv *env, jobject obj, jdouble n, jint b) {

   //std::cout << encode(n, b) << std::endl;
   return env->NewStringUTF(encodeD(n, b).c_str());

}
JNIEXPORT jstring JNICALL Java_base62_Base62JNI_encode__FI(JNIEnv *env, jobject obj, jfloat n, jint b) {

   //std::cout << encode(n, b) << std::endl;
   return env->NewStringUTF(encodeF(n, b).c_str());

}

JNIEXPORT jlong JNICALL Java_base62_Base62JNI_decode(JNIEnv *env, jobject obj, jstring s, jint b)
{
   return decode(std::string(env->GetStringUTFChars(s, NULL)), b);
}

JNIEXPORT jdouble JNICALL Java_base62_Base62JNI_decodeD(JNIEnv *env, jobject obj, jstring s, jint b)
{
   return decodeD(std::string(env->GetStringUTFChars(s, NULL)), b);
}

JNIEXPORT jfloat JNICALL Java_base62_Base62JNI_decodeF(JNIEnv *env, jobject obj, jstring s, jint b)
{
   return decodeF(std::string(env->GetStringUTFChars(s, NULL)), b);
}