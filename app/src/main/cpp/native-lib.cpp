#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_kpit_scenichiking_util_config_Keys_mapBoxKey(JNIEnv *env, jobject object) {
    std::string mapBox_key = "YOUR_API_KEY";
    return env->NewStringUTF(mapBox_key.c_str());
}
