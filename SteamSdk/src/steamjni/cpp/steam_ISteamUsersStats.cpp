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
	if(SteamUserStats()==0){
		return false;
	}
	return SteamUserStats()->RequestCurrentStats();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nStoreStats
  (JNIEnv *, jclass){
	if(SteamUserStats()==0){
		return false;
	}
	return SteamUserStats()->StoreStats();
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nGetAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==0){
		return false;
	}
	char* pchName = "unknown";
	if(pchNamePtr!=NULL){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	bool passInBoolean = false;
	bool methodCallSuccessful = SteamUserStats()->GetAchievement(pchName, &passInBoolean);
	return passInBoolean && methodCallSuccessful;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nSetAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==0){
		return false;
	}
	char* pchName = "unknown";
	if(pchNamePtr!=NULL){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	return SteamUserStats()->SetAchievement(pchName);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nClearAchievement
(JNIEnv *env, jclass, jobject pchNamePtr){
	if(SteamUserStats()==0){
		return false;
	}
	char* pchName = "unknown";
	if(pchNamePtr!=NULL){
		pchName = (((char *)(*env).GetDirectBufferAddress(pchNamePtr)));
	}
	return SteamUserStats()->ClearAchievement(pchName);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindLeaderboard(JNIEnv * env, jclass callingClass, jstring leaderboardName){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindLeaderboard(leaderboardNameCpp);

	CCallResult<SteamCallbackClass, LeaderboardFindResult_t>* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardFindResult_t>();
	SteamCallbackClass::getInstance()->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nFindOrCreateLeaderboard(JNIEnv * env, jclass callingClass, jstring leaderboardName, jint eLeaderboardSortMethod, jint eLeaderboardDisplayType){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindOrCreateLeaderboard(leaderboardNameCpp, (ELeaderboardSortMethod)eLeaderboardSortMethod, (ELeaderboardDisplayType)eLeaderboardDisplayType);

	CCallResult<SteamCallbackClass, LeaderboardFindResult_t>* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardFindResult_t>();
	SteamCallbackClass::getInstance()->findLeaderboardVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnFindLeaderboard);

	// cleanup
	env->ReleaseStringUTFChars(leaderboardName, leaderboardNameCpp);
}

JNIEXPORT jstring JNICALL Java_steam_ISteamUserStats_nGetLeaderboardName(JNIEnv * env, jclass callingClass, jlong leaderboardID){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardName = SteamUserStats()->GetLeaderboardName(leaderboardID);
	jstring leaderboardNameJava = env->NewStringUTF(leaderboardName);

	return leaderboardNameJava;
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardEntryCount(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardEntryCount(hSteamLeaderboard);
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardSortMethod(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardSortMethod(hSteamLeaderboard);
}

JNIEXPORT jint JNICALL Java_steam_ISteamUserStats_nGetLeaderboardDisplayType(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	return SteamUserStats()->GetLeaderboardDisplayType(hSteamLeaderboard);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntries(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eleaderboardDataRequest, jint rangeStart, jint rangeEnd){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntries(hSteamLeaderboard, (ELeaderboardDataRequest)eleaderboardDataRequest, rangeStart, rangeEnd);

	CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>();
	SteamCallbackClass::getInstance()->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnLeaderboardScoresDownloaded);
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nDownloadLeaderboardEntriesForUsers(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jlong prgUsers, jint cUsers){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	CSteamID steamID((uint64)prgUsers);
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntriesForUsers(hSteamLeaderboard, &steamID, cUsers);

	CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>();
	SteamCallbackClass::getInstance()->leaderboardScoresDownloadedVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, SteamCallbackClass::getInstance(), &SteamCallbackClass::OnLeaderboardScoresDownloaded);
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamUserStats_nGetDownloadedLeaderboardEntry(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboardEntries, jint index, jobject pLeaderboardEntryJava, jobject pDetailsBuffer){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	LeaderboardEntry_t pLeaderboardEntry;
	int32 * pDetails = (int32 *)env->GetDirectBufferAddress(pDetailsBuffer);
	if(pDetails == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUserStats_nGetDownloadedLeaderboardEntry: Couldn't get byte buffer address");
	}
	int cDetailsMax = env->GetDirectBufferCapacity(pDetailsBuffer)/4;

	bool isEntryRetrievalSuccessful = SteamUserStats()->GetDownloadedLeaderboardEntry(hSteamLeaderboardEntries, index, &pLeaderboardEntry, pDetails, cDetailsMax);

	jclass cSteamIDClass = env->FindClass("steam/CSteamID");
	if(cSteamIDClass == 0){
		SteamCallbackClass::throwException(env, "Couldn't find steam/CSteamID class");
	}

	jmethodID cSteamIDConstructor = env->GetMethodID(cSteamIDClass, "<init>", "(J)V");
	if(cSteamIDConstructor == 0){
		SteamCallbackClass::throwException(env, "Couldn't find steam/CSteamID constructor (J)V");
	}

	jobject steamIDJava = env->NewObject(cSteamIDClass, cSteamIDConstructor, pLeaderboardEntry.m_steamIDUser.ConvertToUint64());
	if(steamIDJava == 0){
		SteamCallbackClass::throwException(env, "Couldn't create new CSteamID object");
	}

	jclass classLeaderboardEntry_t = env->FindClass("steam/LeaderboardEntry_t");
	if(classLeaderboardEntry_t == 0){
		SteamCallbackClass::throwException(env, "Couldn't find class steam/LeaderboardEntry_t");
	}

	jmethodID setSteamIDMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_steamIDUser", "(Lsteam/CSteamID;)V");
	if(setSteamIDMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_steamIDUser(Lsteam/CSteamID;)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setSteamIDMethod, steamIDJava);

	jmethodID setM_nGlobalRankMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_nGlobalRank", "(I)V");
	if(setM_nGlobalRankMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_nGlobalRank(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_nGlobalRankMethod, pLeaderboardEntry.m_nGlobalRank);

	jmethodID setM_nScoreMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_nScore", "(I)V");
	if(setM_nScoreMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_nScore(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_nScoreMethod, pLeaderboardEntry.m_nScore);

	jmethodID setM_cDetailsMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_cDetails", "(I)V");
	if(setM_cDetailsMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_cDetails(I)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_cDetailsMethod, pLeaderboardEntry.m_cDetails);

	jmethodID setM_hUGCMethod = env->GetMethodID(classLeaderboardEntry_t, "setM_hUGC", "(J)V");
	if(setM_hUGCMethod == 0){
		SteamCallbackClass::throwException(env, "Couldn't find method setM_hUGC(J)V");
	}
	env->CallVoidMethod(pLeaderboardEntryJava, setM_hUGCMethod, pLeaderboardEntry.m_hUGC);

	return isEntryRetrievalSuccessful;
}

JNIEXPORT void JNICALL Java_steam_ISteamUserStats_nUploadLeaderboardScore(JNIEnv * env, jclass callingClass, jlong hSteamLeaderboard, jint eLeaderboarduploadScoreMethod, jint nScore, jobject pScoreDetailsBuffer){
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	int32 * pScoreDetails = (int32*)env->GetDirectBufferAddress(pScoreDetailsBuffer);
	if(pScoreDetails == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamUserStats_nUploadLeaderboardScore: couldnt get direct buffer address.");
	}
	int32 cScoreDetailsCount = (int32)(env->GetDirectBufferCapacity(pScoreDetailsBuffer)/4);
	SteamAPICall_t apiCall = SteamUserStats()->UploadLeaderboardScore(hSteamLeaderboard, (ELeaderboardUploadScoreMethod)eLeaderboarduploadScoreMethod, nScore, pScoreDetails, cScoreDetailsCount);

	CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t>* steamCallResult = new CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t>();
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
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindLeaderboard(leaderboardNameCpp);

	CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>();
	SteamUserStatsListener* steamListener = reinterpret_cast<SteamUserStatsListener*>(callbackClassPtr);
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
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(leaderboardName, &isCopy);
	SteamAPICall_t apiCall = SteamUserStats()->FindOrCreateLeaderboard(leaderboardNameCpp, (ELeaderboardSortMethod)eLeaderboardSortMethod, (ELeaderboardDisplayType)eLeaderboardDisplayType);

	CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>();
	SteamUserStatsListener* steamUserStatsListener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
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
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntries(hSteamLeaderboard, (ELeaderboardDataRequest)eleaderboardDataRequest, rangeStart, rangeEnd);

	CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>();
	SteamUserStatsListener* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
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
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	CSteamID steamID((uint64)prgUsers);
	SteamAPICall_t apiCall = SteamUserStats()->DownloadLeaderboardEntriesForUsers(hSteamLeaderboard, &steamID, cUsers);

	CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>();
	SteamUserStatsListener* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
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
	if(SteamUserStats() == 0){
		SteamCallbackClass::throwException(env, "SteamUserStats() returned null. Steam must not be running.");
	}
	int32 * pScoreDetails = (int32*)env->GetDirectBufferAddress(pScoreDetailsBuffer);
	if(pScoreDetails == 0){
		SteamUserStatsListener::throwException(env, "Java_steam_ISteamUserStats_nUploadLeaderboardScore: couldnt get direct buffer address.");
	}
	int32 cScoreDetailsCount = (int32)(env->GetDirectBufferCapacity(pScoreDetailsBuffer)/4);
	SteamAPICall_t apiCall = SteamUserStats()->UploadLeaderboardScore(hSteamLeaderboard, (ELeaderboardUploadScoreMethod)eLeaderboarduploadScoreMethod, nScore, pScoreDetails, cScoreDetailsCount);

	CCallResult<SteamUserStatsListener, LeaderboardScoreUploaded_t>* steamCallResult = new CCallResult<SteamUserStatsListener, LeaderboardScoreUploaded_t>();
	SteamUserStatsListener* listener = reinterpret_cast<SteamUserStatsListener*>(listenerPtr);
	listener->cleanup(false);
	listener->uploadScoreVector.push_back(steamCallResult);
	steamCallResult->Set(apiCall, listener, &SteamUserStatsListener::OnUploadScore);
}
