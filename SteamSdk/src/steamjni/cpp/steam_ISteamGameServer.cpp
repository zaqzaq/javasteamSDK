/*
 * 
 */
#include "steam_ISteamGameServer.h"
#include "SteamCallbackClass.h"
#include <steam/steam_api.h>
#include <steam/steam_gameserver.h>
#include <iostream>

JNIEXPORT void JNICALL Java_steam_ISteamGameServer_LogOn
  (JNIEnv *env, jclass theJavaClass){
	  if(SteamGameServer() == NULL){
		  steamjni::SteamCallbackClass::log(env, "LogOn()->SteamGameServer() returned null");
		  return;
	  }
	  SteamGameServer()->LogOnAnonymous();
}

JNIEXPORT void JNICALL Java_steam_ISteamGameServer_LogOff
(JNIEnv *env, jclass theJavaClass){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "LogOff()->SteamGameServer() returned null");
		return;
	}
	SteamGameServer()->LogOff();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamGameServer_BLoggedOn
(JNIEnv *env, jclass theJavaClass){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "BLoggedOn()->SteamGameServer() returned null");
		return false;
	}
	return SteamGameServer()->BLoggedOn();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamGameServer_BSecure
(JNIEnv *env, jclass theJavaClass){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "BSecure()->SteamGameServer() returned null");
		return false;
	}
	return SteamGameServer()->BSecure();
}

JNIEXPORT jlong JNICALL Java_steam_ISteamGameServer_nGetSteamID
(JNIEnv *env, jclass theJavaClass){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "GetSteamID()->SteamGameServer() returned null");
		return 0;
	}
	return SteamGameServer()->GetSteamID().ConvertToUint64();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamGameServer_nSendUserConnectAndAuthenticate
(JNIEnv *env, jclass theJavaClass, jint unIPClient, jbyteArray pvAuthBlob, jlong pSteamIDUser){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "SendUserConnectAndAuthenticate()->SteamGameServer() returned null");
		return false;
	}
	if(pvAuthBlob==NULL){
		return false;
	}
	jbyte* nativePVAuthBlob = env->GetByteArrayElements(pvAuthBlob, 0);
	int cubAuthBlobSize = env->GetArrayLength(pvAuthBlob);
	CSteamID nativePSteamIDUser((uint64)pSteamIDUser);
	bool returnValue = SteamGameServer()->SendUserConnectAndAuthenticate(unIPClient, nativePVAuthBlob, cubAuthBlobSize, &nativePSteamIDUser);
	env->ReleaseByteArrayElements(pvAuthBlob, nativePVAuthBlob, 0);

	return returnValue;
}

JNIEXPORT void JNICALL Java_steam_ISteamGameServer_nSendUserDisconnect
(JNIEnv *env, jclass theJavaClass, jlong steamIDUser){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "SendUserDisconnect()->SteamGameServer() returned null");
		return;
	}
	CSteamID nativeSteamIDUser((uint64)steamIDUser);
	SteamGameServer()->SendUserDisconnect(nativeSteamIDUser);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamGameServer_nBUpdateUserData
(JNIEnv *env, jclass theJavaClass, jlong steamIDUser, jstring pchPlayerName, jint uScore){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "BUpdateUserData()->SteamGameServer() returned null");
		return false;
	}
	CSteamID nativeSteamIDUser((uint64)steamIDUser);
	const char* nativePlayerName = "player";
	if(pchPlayerName!=NULL){
		nativePlayerName = env->GetStringUTFChars(pchPlayerName, 0);
	}
	bool returnValue = SteamGameServer()->BUpdateUserData(nativeSteamIDUser, nativePlayerName, uScore);
	if(pchPlayerName!=NULL){
		env->ReleaseStringUTFChars(pchPlayerName, nativePlayerName);
	}
	return returnValue;
}

JNIEXPORT jint JNICALL Java_steam_ISteamGameServer_GetPublicIP
(JNIEnv *env, jclass javaClass){
	if(SteamGameServer()==NULL){
		steamjni::SteamCallbackClass::log(env, "Java_steam_ISteamGameServer_GetPublicIP()->SteamGameServer() returned null");
		return 0;
	}
	return SteamGameServer()->GetPublicIP();
}
