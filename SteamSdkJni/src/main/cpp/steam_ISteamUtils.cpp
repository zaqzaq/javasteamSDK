/*
 * steam_ISteamUtils.cpp
 *
 *  Created on: Nov 17, 2009
 *      Author: karl
 */

#include "steam_ISteamUtils.h"
#include "SteamCallbackClass.h"
#include <steam/steam_api.h>
#include <jni.h>

using steamjni::SteamCallbackClass;

JNIEXPORT jlong JNICALL Java_steam_ISteamUtils_nGetAppID
  (JNIEnv *, jclass){
	if(SteamUtils()==nullptr){
		return (jlong)0;
	}
	return (jlong)SteamUtils()->GetAppID();
}

JNIEXPORT void JNICALL Java_steam_ISteamUtils_nSetOverlayNotificationPosition
(JNIEnv *, jclass, jint pos){
	if(SteamUtils()==nullptr){
		return;
	}
	SteamUtils()->SetOverlayNotificationPosition((ENotificationPosition)pos);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUtils_nGetImageSize(JNIEnv * env, jclass callingClass, jint iImage, jobject steamImageDimensions){
	if(SteamUtils() == nullptr){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageSize: SteamUtils() is null.");
		return false;
	}
	uint32 width = 0;
	uint32 height = 0;
	bool steamCallBoolean = SteamUtils()->GetImageSize(iImage, &width, &height);

	jclass imageHolderClass = env->FindClass("steam/SteamImageDimensions");
	if(imageHolderClass == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find class steam/SteamImageDimensions.");
	}
	jmethodID setWidthMethod = env->GetMethodID(imageHolderClass, "setWidth", "(I)V");
	if(setWidthMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setWidth(I)V.");
	}
	env->CallVoidMethod(steamImageDimensions, setWidthMethod, width);

	jmethodID setHeightMethod = env->GetMethodID(imageHolderClass, "setHeight", "(I)V");
	if(setHeightMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setHeight(I)V.");
	}
	env->CallVoidMethod(steamImageDimensions, setHeightMethod, height);

	return steamCallBoolean;
}


JNIEXPORT jboolean JNICALL Java_steam_ISteamUtils_nGetImageRGBA(JNIEnv * env, jclass callingClass, jint iImage, jobject pubDestBuffer){
	if(SteamUtils() == nullptr){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageRGBA: SteamUtils() is null.");
		return false;
	}
	auto * pubDest = (uint8 *)env->GetDirectBufferAddress(pubDestBuffer);
	int nDestBufferSize = env->GetDirectBufferCapacity(pubDestBuffer);
	if(pubDest == nullptr){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageRGBA: Couldn't get direct byte buffer address.");
	}
	bool getImageResult = SteamUtils()->GetImageRGBA(iImage, pubDest, nDestBufferSize);
	return getImageResult;
}
