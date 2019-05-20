/*
 * SteamCallbackClass.h
 *
 *  Created on: Jun 18, 2009
 *      Author: karl
 */

#ifndef STEAMCALLBACKCLASS_H_
#define STEAMCALLBACKCLASS_H_

#include <jni.h>
#include <steam/steam_api.h>
#include <vector>

#include <memory>

namespace steamjni {

class SteamCallbackClass {
	private:
		JavaVM *jvm;
		void log(const char* theString);
		explicit SteamCallbackClass(JavaVM *jvm);
		static std::unique_ptr<SteamCallbackClass> instance;
	public:
		std::vector<CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t>*> leaderboardScoresDownloadedVector;
		std::vector<CCallResult<SteamCallbackClass, LeaderboardFindResult_t>*> findLeaderboardVector;
		std::vector<CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t>*> uploadScoreVector;

		static void throwException(JNIEnv * env, const char * str);
		static void log(JNIEnv *env, const char* theString);
		static void log(JavaVM *jvm, const char* theString);
		static SteamCallbackClass * createInstance(JavaVM * jvm);
		static void releaseInstance();
		static SteamCallbackClass * getInstance();
		virtual ~SteamCallbackClass();
		virtual void cleanup(bool force);
		STEAM_CALLBACK(steamjni::SteamCallbackClass, OnUserStatsReceived, UserStatsReceived_t, m_CallbackUserStatsReceived);
		STEAM_CALLBACK(steamjni::SteamCallbackClass, OnUserStatsStored, UserStatsStored_t, m_CallbackUserStatsStored);
		STEAM_CALLBACK(steamjni::SteamCallbackClass, OnAchievementStored, UserAchievementStored_t, m_CallbackAchievementStored);

//		STEAM_CALLBACK(steamjni::SteamCallbackClass, OnLeaderboardScoresDownloaded, LeaderboardScoresDownloaded_t, m_OnLeaderboardScoresDownloaded);
		void OnLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t *pFindLearderboardResult, bool bIOFailure);
//		CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t> m_OnLeaderboardScoresDownloaded;

//		STEAM_CALLBACK(steamjni::SteamCallbackClass, OnFindLeaderboard, LeaderboardFindResult_t, m_OnFindLeaderboard);
		void OnFindLeaderboard(LeaderboardFindResult_t *pLeaderboardFindResult, bool bIOFailure);
//		CCallResult<SteamCallbackClass, LeaderboardFindResult_t> m_OnFindLeaderboard;

		//STEAM_CALLBACK(steamjni::SteamCallbackClass, OnUploadScore, LeaderboardScoreUploaded_t, m_OnUploadScore);
		void OnUploadScore(LeaderboardScoreUploaded_t *pLeaderboardScoreUploaded, bool bIOFailure);
//		CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t> m_OnUploadScore;
};

}

#endif /* STEAMCALLBACKCLASS_H_ */
