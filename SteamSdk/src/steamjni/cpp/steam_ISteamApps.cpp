#include <steam/steamclientpublic.h>
#include <steam/steamtypes.h>

#include <jni_md.h>
#include <jni.h>

#include "steam_ISteamApps.h"

#include <iostream>
#include <stdint.h>
#include <steam/steam_api.h>

#include "SteamCallbackClass.h"

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsSubscribed(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsSubscribed();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsLowViolence(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsLowViolence();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsCyberCafe(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsCybercafe();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsVACBanned(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsVACBanned();
}

JNIEXPORT jstring JNICALL Java_steam_ISteamApps_nGetCurrentGameLanguage(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	steamjni::SteamCallbackClass::log(env, "GetCurrentGameLanguages is not implemented");
	return 0;
}

JNIEXPORT jstring JNICALL Java_steam_ISteamApps_nGetAvailableGameLanguages(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	steamjni::SteamCallbackClass::log(env, "GetAvailableGameLanguages is not implemented");
	return 0;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsSubscribedApp(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsSubscribedApp(appId);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsDlcInstalled(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsDlcInstalled(appId);
}

JNIEXPORT jlong JNICALL Java_steam_ISteamApps_nGetEarliestPurchaseUnixTime(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return 0;
	}
	return SteamApps()->GetEarliestPurchaseUnixTime(appId);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsSubscribedFromFreeWeekend(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsSubscribedFromFreeWeekend();
}

JNIEXPORT jint JNICALL Java_steam_ISteamApps_nGetDLCCount(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	return SteamApps()->GetDLCCount();
}

JNIEXPORT void JNICALL Java_steam_ISteamApps_nGetDLCDataByIndex(JNIEnv* env, jclass callingClass, jint dlcIndex, jobject bufferObject) {
	if (SteamApps() == 0) {
		return;
	}
	steamjni::SteamCallbackClass::log(env, "GetDLCDataByIndex is not implemented");
	return;
}

JNIEXPORT void JNICALL Java_steam_ISteamApps_nInstallDLC(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return;
	}
	SteamApps()->InstallDLC(appId);
}

JNIEXPORT void JNICALL Java_steam_ISteamApps_nUninstallDLC(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return;
	}
	SteamApps()->UninstallDLC(appId);
}

JNIEXPORT jstring JNICALL Java_steam_ISteamApps_nGetCurrentBetaName(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	const int pchNameLength = 128;
	char pchName[pchNameLength];
	bool success = SteamApps()->GetCurrentBetaName(pchName, pchNameLength);
	if (success) {
		return env->NewStringUTF(pchName);
	} else {
		return 0;
	}
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nMarkContentCorrupt(JNIEnv* env, jclass callingClass, jboolean missingFilesOnly) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->MarkContentCorrupt(missingFilesOnly);
}

JNIEXPORT jint JNICALL Java_steam_ISteamApps_nGetInstalledDepots(JNIEnv* env, jclass callingClass, jlong appId, jobject bufferObject) {
	if (SteamApps() == 0) {
		return false;
	}
	steamjni::SteamCallbackClass::log(env, "GetInstalledDepots is not implemented");
	return 0;
}

JNIEXPORT jstring JNICALL Java_steam_ISteamApps_nGetAppInstallDir(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return 0;
	}
	const int pchFolderLength = 512;
	char pchFolder[pchFolderLength];
	uint32 length = SteamApps()->GetAppInstallDir(appId, pchFolder, pchFolderLength);
	return env->NewStringUTF(pchFolder);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamApps_nBIsAppInstalled(JNIEnv* env, jclass callingClass, jlong appId) {
	if (SteamApps() == 0) {
		return false;
	}
	return SteamApps()->BIsAppInstalled(appId);
}

JNIEXPORT jlong JNICALL Java_steam_ISteamApps_nGetAppOwner(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	CSteamID steamId = SteamApps()->GetAppOwner();
	uint64 steamIdUint = steamId.ConvertToUint64();
	return steamIdUint;
}

JNIEXPORT jint JNICALL Java_steam_ISteamApps_nGetAppBuildId(JNIEnv* env, jclass callingClass) {
	if (SteamApps() == 0) {
		return 0;
	}
	return SteamApps()->GetAppBuildId();
}
