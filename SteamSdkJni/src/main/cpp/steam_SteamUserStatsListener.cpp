#include "steam_SteamUserStatsListener.h"
#include "SteamUserStatsListener.h"


using steamjni::SteamUserStatsListener;


JNIEXPORT jlong JNICALL Java_steam_SteamUserStatsListener_nInitializeCppCallback
  (JNIEnv *env, jobject callbackClass){
	JavaVM *jvm = nullptr;
	env->GetJavaVM(&jvm);
	auto* listener = new SteamUserStatsListener(jvm, callbackClass);
	return reinterpret_cast<jlong>(listener);
}


/*
 * Class:     steam_SteamUserStatsListener
 * Method:    nFreeCppCallback
 * Signature: ()V
 */

JNIEXPORT void JNICALL Java_steam_SteamUserStatsListener_nFreeCppCallback
  (JNIEnv *, jobject, jlong cppPointer){
	auto* listener = reinterpret_cast<SteamUserStatsListener*>(cppPointer);
	if(listener != nullptr){
		delete(listener);
	}
}
