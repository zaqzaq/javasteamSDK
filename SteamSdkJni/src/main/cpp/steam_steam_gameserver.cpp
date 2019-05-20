#include "steam_steam_gameserver.h"
#include "SteamCallbackClass.h"
#include "SteamGameServerCallbackClass.h"
#include <steam/steam_api.h>
#include <steam/steam_gameserver.h>
#include <iostream>
#include <string>
#include <sstream>
#include <exception>
using namespace std;

extern "C" void __cdecl SteamGameServerAPIDebugTextHook( int nSeverity, const char *pchDebugText ){
	std::cout << pchDebugText << std::endl;
}

steamjni::SteamGameServerCallbackClass *steamGameServerCallback = nullptr;

JNIEXPORT jboolean JNICALL Java_steam_steam_1gameserver_nSteamGameServer_1Init
  (JNIEnv *env, jclass javaClass, jint unIP, jint usPort, jint usGamePort, jint usSpectatorPort, jint usQueryPort, jint eServerMode, jstring pchGameDir, jstring pchVersionString){
	  JavaVM *jvm = nullptr;
	  env->GetJavaVM(&jvm);
	  if(jvm!=nullptr){
	 	steamGameServerCallback = new steamjni::SteamGameServerCallbackClass(jvm);
	  }else{
		cerr << "NATIVE: Failed to get JVM pointer" << endl;
	  }

	  const char *nativePchGameDir = "game dir";
	  const char *nativePchVersionString = "0.0.0";
	  if(pchGameDir!=nullptr){
		nativePchGameDir = env->GetStringUTFChars(pchGameDir, nullptr);
	  }
	  if(pchVersionString!=nullptr){
		nativePchVersionString = env->GetStringUTFChars(pchVersionString, nullptr);
	  }
	  bool returnValue = SteamGameServer_Init(unIP, (uint16)usPort, (uint16)usGamePort, (uint16)usQueryPort, (EServerMode)eServerMode, nativePchVersionString);
	  if(pchGameDir!=nullptr){
		env->ReleaseStringUTFChars(pchGameDir, nativePchGameDir);
	  }
	  if(pchVersionString!=nullptr){
		env->ReleaseStringUTFChars(pchVersionString, nativePchVersionString);
	  }

	  if(SteamUtils()!=nullptr){
		  SteamUtils()->SetWarningMessageHook( &SteamGameServerAPIDebugTextHook );
	  }

	  return returnValue;
}

JNIEXPORT void JNICALL Java_steam_steam_1gameserver_SteamGameServer_1Shutdown
(JNIEnv *env, jclass javaClass){
	SteamGameServer_Shutdown();
	if(steamGameServerCallback!=nullptr){
		delete(steamGameServerCallback);
		steamGameServerCallback = nullptr;
	}
}

JNIEXPORT void JNICALL Java_steam_steam_1gameserver_SteamGameServer_1RunCallbacks
(JNIEnv *evn, jclass javaClass){
	SteamGameServer_RunCallbacks();
}

JNIEXPORT jboolean JNICALL Java_steam_steam_1gameserver_SteamGameServer_1BSecure
(JNIEnv *evn, jclass javaClass){
	return SteamGameServer_BSecure();
}

JNIEXPORT jlong JNICALL Java_steam_steam_1gameserver_SteamGameServer_1GetSteamID
(JNIEnv *, jclass){
	return SteamGameServer_GetSteamID();
}
