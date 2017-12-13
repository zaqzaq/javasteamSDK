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
	if(SteamUtils()==0){
		return (jlong)0;
	}
	return (jlong)SteamUtils()->GetAppID();
}

JNIEXPORT void JNICALL Java_steam_ISteamUtils_nSetOverlayNotificationPosition
(JNIEnv *, jclass, jint pos){
	if(SteamUtils()==0){
		return;
	}
	SteamUtils()->SetOverlayNotificationPosition((ENotificationPosition)pos);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUtils_nGetImageSize(JNIEnv * env, jclass callingClass, jint iImage, jobject steamImageDimensions){
	if(SteamUtils() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageSize: SteamUtils() is null.");
		return false;
	}
	uint32 width = 0;
	uint32 height = 0;
	bool steamCallBoolean = SteamUtils()->GetImageSize(iImage, &width, &height);

	jclass imageHolderClass = env->FindClass("steam/SteamImageDimensions");
	if(imageHolderClass == 0){
		SteamCallbackClass::throwException(env, "Couldn't find class steam/SteamImageDimensions.");
	}
	jmethodID setWidthMethod = env->GetMethodID(imageHolderClass, "setWidth", "(I)V");
	if(setWidthMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setWidth(I)V.");
	}
	env->CallVoidMethod(steamImageDimensions, setWidthMethod, width);

	jmethodID setHeightMethod = env->GetMethodID(imageHolderClass, "setHeight", "(I)V");
	if(setHeightMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setHeight(I)V.");
	}
	env->CallVoidMethod(steamImageDimensions, setHeightMethod, height);

	return steamCallBoolean;
}


JNIEXPORT jboolean JNICALL Java_steam_ISteamUtils_nGetImageRGBA(JNIEnv * env, jclass callingClass, jint iImage, jobject pubDestBuffer){
	if(SteamUtils() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageRGBA: SteamUtils() is null.");
		return false;
	}
	uint8 * pubDest = (uint8 *)env->GetDirectBufferAddress(pubDestBuffer);
	int nDestBufferSize = env->GetDirectBufferCapacity(pubDestBuffer);
	if(pubDest == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUtils_nGetImageRGBA: Couldn't get direct byte buffer address.");
	}
	bool getImageResult = SteamUtils()->GetImageRGBA(iImage, pubDest, nDestBufferSize);
	return getImageResult;
}
