#include "steam_SteamUserStatsListener.h"
#include "SteamUserStatsListener.h"


using steamjni::SteamUserStatsListener;


JNIEXPORT jlong JNICALL Java_steam_SteamUserStatsListener_nInitializeCppCallback
  (JNIEnv *env, jobject callbackClass){
	JavaVM *jvm = 0;
	env->GetJavaVM(&jvm);
	SteamUserStatsListener* listener = new SteamUserStatsListener(jvm, callbackClass);
	return reinterpret_cast<jlong>(listener);
}


/*
 * Class:     steam_SteamUserStatsListener
 * Method:    nFreeCppCallback
 * Signature: ()V
 */

JNIEXPORT void JNICALL Java_steam_SteamUserStatsListener_nFreeCppCallback
  (JNIEnv *, jobject, jlong cppPointer){
	SteamUserStatsListener* listener = reinterpret_cast<SteamUserStatsListener*>(cppPointer);
	if(listener != 0){
		delete(listener);
	}
}
