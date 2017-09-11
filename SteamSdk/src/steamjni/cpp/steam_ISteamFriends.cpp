/*
 * steam_ISteamUsersStats.cpp
 *
 */
#include "steam_ISteamFriends.h"
#include "SteamCallbackClass.h"
#include <steam/steam_api.h>
#include <iostream>

using steamjni::SteamCallbackClass;

JNIEXPORT jstring JNICALL Java_steam_ISteamFriends_nGetPersonaName
  (JNIEnv *env, jclass){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return env->NewStringUTF("");
	}
    const char* personaName = steamFriends->GetPersonaName();
	return env->NewStringUTF(personaName);
}

JNIEXPORT jint JNICALL Java_steam_ISteamFriends_nGetFriendCount
  (JNIEnv *, jclass, jint flag){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return (jint)0;
	}
	return (jint)steamFriends->GetFriendCount((int)flag);
}

JNIEXPORT jlong JNICALL Java_steam_ISteamFriends_nGetFriendByIndex
  (JNIEnv *, jclass, jint iFriend, jint flag){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return (jlong)0;
	}
	return (jlong)steamFriends->GetFriendByIndex((int)iFriend, (int)flag).ConvertToUint64();
}

JNIEXPORT jstring JNICALL Java_steam_ISteamFriends_nGetFriendPersonaName
(JNIEnv *env, jclass, jlong ulSteamID){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return env->NewStringUTF("");
	}
	CSteamID steamIDFriend((uint64)ulSteamID);
	const char* personaName = steamFriends->GetFriendPersonaName(steamIDFriend);
	return env->NewStringUTF(personaName);
}

JNIEXPORT jint JNICALL Java_steam_ISteamFriends_nGetClanCount
(JNIEnv *, jclass){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return (jint)0;
	}
	return (jint)steamFriends->GetClanCount();
}

JNIEXPORT jlong JNICALL Java_steam_ISteamFriends_nGetClanByIndex
(JNIEnv *, jclass, jint iClan){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return (jlong)0;
	}
	return (jlong)steamFriends->GetClanByIndex((int)iClan).ConvertToUint64();
}

JNIEXPORT jstring JNICALL Java_steam_ISteamFriends_nGetClanName
(JNIEnv *env, jclass, jlong ulSteamID){
	ISteamFriends* steamFriends = SteamFriends();
	if(steamFriends==0){
		return env->NewStringUTF("");
	}
	CSteamID steamIDClan((uint64)ulSteamID);
	const char* personaName = steamFriends->GetClanName(steamIDClan);
	return env->NewStringUTF(personaName);
}

JNIEXPORT jint JNICALL Java_steam_ISteamFriends_nGetSmallFriendAvatar(JNIEnv * env, jclass callingClass, jlong steamID){
	if(SteamFriends()==0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamFriends_nGetSmallFriendAvatar: SteamFriends() is null");
		return 0;
	}

	CSteamID cSteamID((uint64)steamID);
	int iImage = SteamFriends()->GetSmallFriendAvatar(cSteamID);

	return iImage;
}
