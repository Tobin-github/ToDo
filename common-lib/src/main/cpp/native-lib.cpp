#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
Java_top_tobin_common_cpp_TestCpp_stringFromJNI(JNIEnv* env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}