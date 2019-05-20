/*
 * steam_ISteamUser.cpp
 *
 */
#include "steam_ISteamUser.h"
#include "SteamCallbackClass.h"
#include <steam/steam_api.h>
#include <iostream>

using steamjni::SteamCallbackClass;

JNIEXPORT jlong JNICALL Java_steam_ISteamUser_nGetSteamID
(JNIEnv *env, jclass javaClass){
	if(SteamUser() == nullptr){
		  steamjni::SteamCallbackClass::log(env, "Java_steam_ISteamUser_nGetSteamID()->SteamUser() returned null");
		  return 0;
	}
	return (jlong)SteamUser()->GetSteamID().ConvertToUint64();
}

JNIEXPORT jint JNICALL Java_steam_ISteamUser_nInitiateGameConnection
(JNIEnv *env, jclass javaClass, jobject byteBuffer, jlong steamIDGameServer, jint unIPServer, jint usPortServer, jboolean bSecure){
	if(SteamUser() == nullptr){
	  steamjni::SteamCallbackClass::log(env, "Java_steam_ISteamUser_nInitiateGameConnection()->SteamUser() returned null");
	  return 0;
	}
	CSteamID serverSteamID((uint64)steamIDGameServer);
	void* pAuthBlob = nullptr;
	int cbMaxAuthBlob = 0;
	if(byteBuffer!=nullptr){
		pAuthBlob = env->GetDirectBufferAddress(byteBuffer);
		if(pAuthBlob == nullptr){
			SteamCallbackClass::throwException(env, "Failed to get address of direct byte buffer");
		}
		cbMaxAuthBlob = (int)env->GetDirectBufferCapacity(byteBuffer);
	}
	return SteamUser()->InitiateGameConnection(pAuthBlob, cbMaxAuthBlob, serverSteamID, unIPServer, (uint16)usPortServer,
                                               bSecure != 0);
}

JNIEXPORT void JNICALL Java_steam_ISteamUser_TerminateGameConnection
(JNIEnv *env, jclass javaClass, jint unIPServer, jint usPortServer){
	if(SteamUser() == nullptr){
	  steamjni::SteamCallbackClass::log(env, "Java_steam_ISteamUser_TerminateGameConnection()->SteamUser() returned null");
	  return;
	}
	SteamUser()->TerminateGameConnection(unIPServer, (uint16)usPortServer);
}
