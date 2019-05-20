/*
 * SteamUserStatsListener.h
 *
 *  Created on: Jun 18, 2009
 *      Author: karl
 */

#ifndef SteamUserStatsListener_H_
#define SteamUserStatsListener_H_

#include <jni.h>
#include <steam/steam_api.h>
#include <vector>

#include <memory>

namespace steamjni {

class SteamUserStatsListener {
	private:
		jobject javaCallbackClass;
		JavaVM *jvm;
		void log(const char* theString);

	public:
		SteamUserStatsListener(JavaVM *jvm, jobject javaCallbackClass);

		std::vector<CCallResult<SteamUserStatsListener, LeaderboardScoresDownloaded_t>*> leaderboardScoresDownloadedVector;
		std::vector<CCallResult<SteamUserStatsListener, LeaderboardFindResult_t>*> findLeaderboardVector;
		std::vector<CCallResult<SteamUserStatsListener, LeaderboardScoreUploaded_t>*> uploadScoreVector;

		static void throwException(JNIEnv * env, const char * str);
		static void log(JNIEnv *env, const char* theString);
		static void log(JavaVM *jvm, const char* theString);
		virtual ~SteamUserStatsListener();
		virtual void cleanup(bool force);

		void OnLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t *pFindLearderboardResult, bool bIOFailure);

		void OnFindLeaderboard(LeaderboardFindResult_t *pLeaderboardFindResult, bool bIOFailure);

		void OnUploadScore(LeaderboardScoreUploaded_t *pLeaderboardScoreUploaded, bool bIOFailure);
};

}

#endif /* SteamUserStatsListener_H_ */
