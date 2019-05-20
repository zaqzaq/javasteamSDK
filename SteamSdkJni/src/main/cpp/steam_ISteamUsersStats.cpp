/*
 * steam_ISteamUsersStats.cpp
 *
 *  Created on: Jun 17, 2009
 *      Author: karl
 */
#include "steam_ISteamUserStats.h"
#include <steam/steam_api.h>
#include <iostream>
#include <sstream>
#include "SteamCallbackClass.h"
#include "SteamUserStatsListener.h"

using steamjni::SteamCallbackClass;
using steamjni::SteamUserStatsListener;

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nRequestCurrentStats
(JNIEnv *, jclass){
	if(SteamUserStats()==nullptr){
		return false;
	}
	return SteamUserStats()->RequestCurrentStats();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nStoreStats
  (JNIEnv *, jclass){
	if(SteamUserStats()==nullptr){
		return false;
	}
	return SteamUserStats()->StoreStats();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nGetAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==nullptr){
		return false;
	}
	const char* pchName = "unknown";
	if(pchNamePtr!=nullptr){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	bool passInBoolean = false;
	bool methodCallSuccessful = SteamUserStats()->GetAchievement(pchName, &passInBoolean);
	return passInBoolean && methodCallSuccessful;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nSetAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==nullptr){
		return false;
	}
	const char* pchName = "unknown";
	if(pchNamePtr!=nullptr){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	return SteamUserStats()->SetAchievement(pchName);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nClearAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==nullptr){
		return false;
	}
	const char* pchName = "unknown";
	if(pchNamePtr!=nullptr){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	return SteamUserStats()->ClearAchievement(pchName);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindLeaderboard(JNIEnv * env, jclass callingClass, jstring leaderboardName){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindLeaderboard(leaderboardNameCpp);

	auto* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardFindResult_t>();
	SteamCallbackClass::getInstance()->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindOrCreateLeaderboard(JNIEnv * env, jclass callingClass, jstring leaderboardName, jint eLeaderboardSortMethod, jint eLeaderboardDisplayType){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindOrCreateLeaderboard(leaderboardNameCpp, (ELeaderboardSortMethod)eLeaderboardSortMethod, (ELeaderboardDisplayType)eLeaderboardDisplayType);

	auto* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardFindResult_t>();
	SteamCallbackClass::getInstance()->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp);
}

JNIEXPORT jstring JNICALL Java_steam_ISteamUserStats_nGetLeaderboardName(JNIEnv * env, jclass callingClass, jlong leaderboardID){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	const char * leaderboardName = SteamUserStats()->GetLeaderboardName(leaderboardID);
	jstring leaderboardNameJava = env->NewStringUTF(leaderboardName);

	return leaderboardNameJava;
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardEntryCount(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardEntryCount(hSteamLeaderboard);
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardSortMethod(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardSortMethod(hSteamLeaderboard);
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardDisplayType(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardDisplayType(hSteamLeaderboard);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntries(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eleaderboardDataRequest, jint rangeStart, jint rangeEnd){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntries(hSteamLeaderboard, (ELeaderboardDataRequest)eleaderboardDataRequest, rangeStart, rangeEnd);

	auto* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>();
	SteamCallbackClass::getInstance()->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnLeaderboardScoresDownloaded);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntriesForUsers(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jlong prgUsers, jint cUsers){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	CSteamID steamID((uint64)prgUsers);
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntriesForUsers(hSteamLeaderboard, &steamID, cUsers);

	auto* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>();
	SteamCallbackClass::getInstance()->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnLeaderboardScoresDownloaded);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nGetDownloadedLeaderboardEntry(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboardEntries, jint index, jobject pLeaderboardEntryJava, jobject pDetailsBuffer){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	LeaderboardEntry_t pLeaderboardEntry;
	auto * pDetails = (int32 *)env->GetDirectBufferAddress(pDetailsBuffer);
	if(pDetails == nullptr){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUserStats_nGetDownloadedLeaderboardEntry: Couldn't get byte buffer address");
	}
	int cDetailsMax = env->GetDirectBufferCapacity(pDetailsBuffer)/4;

	bool isEntryRetrievalSuccessful = SteamUserStats()->GetDownloadedLeaderboardEntry(hSteamLeaderboardEntries, index, &pLeaderboardEntry, pDetails, cDetailsMax);

	jclass cSteamIDClass = env->FindClass("steam/CSteamID");
	if(cSteamIDClass == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find steam/CSteamID class");
	}

	jmethodID cSteamIDConstructor = env->GetMethodID(cSteamIDClass, "<init>", "(J)V");
	if(cSteamIDConstructor == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find steam/CSteamID constructor (J)V");
	}

	jobject steamIDJava = env->NewObject(cSteamIDClass, cSteamIDConstructor, pLeaderboardEntry.m_steamIDUser.ConvertToUint64());
	if(steamIDJava == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't create new CSteamID object");
	}

	jclass classLeaderboardEntry_t = env->FindClass("steam/LeaderboardEntry_t");
	if(classLeaderboardEntry_t == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find class steam/LeaderboardEntry_t");
	}

	jmethodID setSteamIDMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_steamIDUser", "(Lsteam/CSteamID;)V");
	if(setSteamIDMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_steamIDUser(Lsteam/CSteamID;)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setSteamIDMethod, steamIDJava);

	jmethodID setM_nGlobalRankMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_nGlobalRank", "(I)V");
	if(setM_nGlobalRankMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_nGlobalRank(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_nGlobalRankMethod, pLeaderboardEntry.m_nGlobalRank);

	jmethodID setM_nScoreMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_nScore", "(I)V");
	if(setM_nScoreMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_nScore(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_nScoreMethod, pLeaderboardEntry.m_nScore);

	jmethodID setM_cDetailsMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_cDetails", "(I)V");
	if(setM_cDetailsMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_cDetails(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_cDetailsMethod, pLeaderboardEntry.m_cDetails);

	jmethodID setM_hUGCMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_hUGC", "(J)V");
	if(setM_hUGCMethod == nullptr){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_hUGC(J)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_hUGCMethod, pLeaderboardEntry.m_hUGC);

	return isEntryRetrievalSuccessful;
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nUploadLeaderboardScore(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eLeaderboarduploadScoreMethod, jint nScore, jobject pScoreDetailsBuffer){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	auto * pScoreDetails = (int32*)env->GetDirectBufferAddress(pScoreDetailsBuffer);
	if(pScoreDetails == nullptr){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUserStats_nUploadLeaderboardScore: couldnt get direct buffer address.");
	}
	auto cScoreDetailsCount = (int32)(env->GetDirectBufferCapacity(pScoreDetailsBuffer)/4);
	SteamAPICall_t apiCall = SteamUserStats()->UploadLeaderboardScore(hSteamLeaderboard, (ELeaderboardUploadScoreMethod)eLeaderboarduploadScoreMethod, nScore, pScoreDetails, cScoreDetailsCount);

	auto* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t>();
	SteamCallbackClass::getInstance()->uploadScoreVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnUploadScore);
}

/*
 * new instanced listener methods
 */
/*
 * Class:     steam_ISteamUserStats
 * Method:    nFindLeaderboardListener
 * Signature: (Ljava/lang/String;Lsteam/SteamUserStatsListener;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindLeaderboardListener
  (JNIEnv* env, jclass callingClass , jstring leaderboardName, jlong callbackClassPtr){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindLeaderboard(leaderboardNameCpp);

	auto* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>();
	auto* steamListener = reinterpret_cast<SteamUserStatsListener*>(callbackClassPtr);
	steamListener->cleanup(false);
	steamListener->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, steamListener, &SteamUserStatsListener::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp); // this might not be safe, I have no idea if steam is holding onto the pointer
}

/*
 * Class:     steam_ISteamUserStats
 * Method:    nFindOrCreateLeaderboardListener
 * Signature: (Ljava/lang/String;IILsteam/SteamUserStatsListener;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindOrCreateLeaderboardListener
  (JNIEnv * env, jclass callingClass, jstring leaderboardName, jint eLeaderboardSortMethod, jint eLeaderboardDisplayType, jlong listenerPtr){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindOrCreateLeaderboard(leaderboardNameCpp, (ELeaderboardSortMethod)eLeaderboardSortMethod, (ELeaderboardDisplayType)eLeaderboardDisplayType);

	auto* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>();
	auto* steamUserStatsListener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
	steamUserStatsListener->cleanup(false);
	steamUserStatsListener->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, steamUserStatsListener, &SteamUserStatsListener::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp);
}

/*
 * Class:     steam_ISteamUserStats
 * Method:    nDownloadLeaderboardEntriesListener
 * Signature: (JIIILsteam/SteamUserStatsListener;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntriesListener
(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eleaderboardDataRequest, jint rangeStart, jint rangeEnd, jlong listenerPtr){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntries(hSteamLeaderboard, (ELeaderboardDataRequest)eleaderboardDataRequest, rangeStart, rangeEnd);

	auto* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>();
	auto* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
	listener->cleanup(false);
	listener->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, listener, &SteamUserStatsListener::OnLeaderboardScoresDownloaded);
}

/*
 * Class:     steam_ISteamUserStats
 * Method:    nDownloadLeaderboardEntriesForUsersListener
 * Signature: (JJILsteam/SteamUserStatsListener;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntriesForUsersListener
(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jlong prgUsers, jint cUsers, jlong listenerPtr){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	CSteamID steamID((uint64)prgUsers);
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntriesForUsers(hSteamLeaderboard, &steamID, cUsers);

	auto* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>();
	auto* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
	listener->cleanup(false);
	listener->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, listener, &SteamUserStatsListener::OnLeaderboardScoresDownloaded);
}

/*
 * Class:     steam_ISteamUserStats
 * Method:    nUploadLeaderboardScoreListener
 * Signature: (JIILjava/nio/IntBuffer;Lsteam/SteamUserStatsListener;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nUploadLeaderboardScoreListener
(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eLeaderboarduploadScoreMethod, jint nScore, jobject pScoreDetailsBuffer, jlong listenerPtr){
	if(SteamUserStats() == nullptr){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	auto * pScoreDetails = (int32*)env->GetDirectBufferAddress(pScoreDetailsBuffer);
	if(pScoreDetails == nullptr){
		SteamUserStatsListener::throwException(env, "Java_steam_ISteamUserStats_nUploadLeaderboardScore: couldnt get direct buffer address.");
	}
	auto cScoreDetailsCount = (int32)(env->GetDirectBufferCapacity(pScoreDetailsBuffer)/4);
	SteamAPICall_t apiCall = SteamUserStats()->UploadLeaderboardScore(hSteamLeaderboard, (ELeaderboardUploadScoreMethod)eLeaderboarduploadScoreMethod, nScore, pScoreDetails, cScoreDetailsCount);

	auto* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoreUploaded_t>();
	auto* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
	listener->cleanup(false);
	listener->uploadScoreVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, listener, &SteamUserStatsListener::OnUploadScore);
}
