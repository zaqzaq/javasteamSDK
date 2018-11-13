/*
 * steam_steam_0005fapi.cpp
 *
 *  Created on: Jun 16, 2009
 *      Author: karl
 */

#include "steam_steam_api.h"

#include <iostream>
#include <sstream>

#include "SteamCallbackClass.h"

using namespace std;

JavaVM * jvmGlobalSteamApi;

extern "C" void __cdecl ngSteamClientAPIDebugTextHook( int nSeverity, const char *pchDebugText ){
	std::cout << pchDebugText << std::endl;

	std::stringstream sstream;
	sstream << "SteamClientAPI [" << nSeverity << "] " << pchDebugText << std::endl;
	steamjni::SteamCallbackClass::log(jvmGlobalSteamApi, sstream.str().c_str());
}

extern "C" void __cdecl ngSteamAPIWarningMessageHook_t(int nSeverity, const char * pchDebugText){
	std::cout << pchDebugText << std::endl;

	std::stringstream sstream;
	sstream << "SteamAPI [" << nSeverity << "] " << pchDebugText << std::endl;
	steamjni::SteamCallbackClass::log(jvmGlobalSteamApi, sstream.str().c_str());
}

JNIEXPORT void JNICALL Java_steam_steam_1api_nSteamAPI_1Init(JNIEnv *env, jclass javaClass, jlong steamAppId) {
	JavaVM *jvm = 0;
	env->GetJavaVM(&jvm);
	if(jvm!=0){
		steamjni::SteamCallbackClass::createInstance(jvm);
	}else{
		cerr << "NATIVE: Failed to get JVM pointer" << endl;
	}
	jvmGlobalSteamApi = jvm;

	bool steamRunning = SteamAPI_IsSteamRunning();
	std::stringstream sstream;
	sstream << "SteamAPI_IsSteamRunning = " << steamRunning;
	steamjni::SteamCallbackClass::log(env, sstream.str().c_str());

//	bool restartApp = SteamAPI_RestartAppIfNecessary(steamAppId);
//	sstream.str(std::string());
//	sstream << "SteamAPI_RestartAppIfNecessary = " << restartApp;
//	steamjni::SteamCallbackClass::log(env, sstream.str().c_str());
//	if(restartApp){
//		std::cout << "should restart the app..." << std::endl << std::flush;
//		jclass MyOwnException = env->FindClass("steam/exception/SteamException");
//		if(MyOwnException!=0){
//			env->ThrowNew(MyOwnException, "Flamebreak was not started from Steam.");
//			return;
//		}else{
//			std::cerr << "Failed to find exception steam/exception/SteamException";
//		}
//	}

	steamjni::SteamCallbackClass::log(env, "Going to call SteamAPI_Init");
	bool inited = SteamAPI_Init();
	sstream.clear();
	sstream << "SteamAPI_Init = " << inited;
	steamjni::SteamCallbackClass::log(env, sstream.str().c_str());
	if ( !inited ) {
		jclass MyOwnException = env->FindClass("steam/exception/SteamException");
		if(MyOwnException!=0){
			env->ThrowNew(MyOwnException,"Could not initialize SteamAPI. Steam must be running to call this method.");
			return;
		}else{
			std::cerr << "Failed to find exception steam/exception/SteamException";
		}
	}else{
		steamjni::SteamCallbackClass::log(env, "SteamUtil()->SetWarningMessageHook");
		SteamUtils()->SetWarningMessageHook( &ngSteamAPIWarningMessageHook_t );
		steamjni::SteamCallbackClass::log(env, "SteamUtil()->SetWarningMessageHook done");

		steamjni::SteamCallbackClass::log(env, "SteamClient()->SetWarningMessageHook");
		SteamClient()->SetWarningMessageHook( &ngSteamClientAPIDebugTextHook );
		steamjni::SteamCallbackClass::log(env, "SteamClient()->SetWarningMessageHook done");
//		Dont delete this it's usefull information
//		jmethodID callBackMethodID = env->GetStaticMethodID(javaClass, "callbackFromNative", "()V");
//		if(callBackMethodID!=0){
//			env->CallStaticObjectMethod(javaClass, callBackMethodID);
//		}
//		jmethodID callBackMethodID = env->GetStaticMethodID(javaClass, "logFromNative", "(Ljava/lang/String;)V");
//		if(callBackMethodID!=0){
//			ostringstream nativeStringBuffer;
//			nativeStringBuffer << "blah, append number 20=" << 20 << " another string o_O";
//			jstring javaString = env->NewStringUTF(&nativeStringBuffer.str()[0]);
//			env->CallStaticObjectMethod(javaClass, callBackMethodID, javaString);
//		}

//		Dont delete this it's usefull information
//		UserAchievementStored_t testStruct;
//		steamCallbackClass->OnAchievementStored(&testStruct);
	}
}

JNIEXPORT void JNICALL Java_steam_steam_1api_nSteamAPI_1Shutdown
(JNIEnv *env, jclass javaClass){
	SteamAPI_Shutdown();
	steamjni::SteamCallbackClass::releaseInstance();
}

JNIEXPORT void JNICALL Java_steam_steam_1api_nSteamAPI_1RunCallbacks
  (JNIEnv *env, jclass javaClass){
	SteamAPI_RunCallbacks();
	steamjni::SteamCallbackClass::getInstance()->cleanup(false);
}
