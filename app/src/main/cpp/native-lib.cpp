#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_kpit_scenichiking_util_config_Keys_mapBoxKey(JNIEnv *env, jobject object) {
    std::string mapBox_key = "pk.eyJ1IjoibnVoa29jYSIsImEiOiJjazBwODBhcHYwZmVsM25uNnYxMHIybzAyIn0.9QCILPbCrVOTe5x5UCQY9g";
    return env->NewStringUTF(mapBox_key.c_str());
}
