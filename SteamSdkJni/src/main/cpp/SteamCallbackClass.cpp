/*
 * SteamCallbackClass.cpp
 *
 *  Created on: Jun 18, 2009
 *      Author: karl
 */

#include "SteamCallbackClass.h"

#include <iostream>
#include <string>
#include <sstream>

#include <steam/steam_api.h>
#include <vector>

using namespace std;

namespace steamjni {

    unique_ptr<SteamCallbackClass> SteamCallbackClass::instance;

    SteamCallbackClass::SteamCallbackClass(JavaVM *jvm) :
            m_CallbackUserStatsReceived(this, &SteamCallbackClass::OnUserStatsReceived), m_CallbackUserStatsStored(this,
                                                                                                                   &SteamCallbackClass::OnUserStatsStored),
            m_CallbackAchievementStored(this,
                                        &SteamCallbackClass::OnAchievementStored) {
        this->jvm = jvm;
    }

    SteamCallbackClass *SteamCallbackClass::createInstance(JavaVM *jvm) {
        if (instance == nullptr) {
            instance.reset(new SteamCallbackClass(jvm));
        }
        return instance.get();
    }

    void SteamCallbackClass::releaseInstance() {
        instance.reset(nullptr);
    }

    SteamCallbackClass *SteamCallbackClass::getInstance() {
        return instance.get();
    }

    SteamCallbackClass::~SteamCallbackClass() {
        cleanup(true);
    }

    void SteamCallbackClass::cleanup(bool force) {
        for (auto it =
                leaderboardScoresDownloadedVector.begin(); it != leaderboardScoresDownloadedVector.end();) {
            if ((*it)->IsActive() == 0 || force) {
                CCallResult<SteamCallbackClass, LeaderboardScoresDownloaded_t> *steamCallResult = (*it);
                delete steamCallResult;
                leaderboardScoresDownloadedVector.erase(it);
            } else {
                ++it;
            }
        }

        for (auto it =
                findLeaderboardVector.begin(); it != findLeaderboardVector.end();) {
            if ((*it)->IsActive() == 0 || force) {
                CCallResult<SteamCallbackClass, LeaderboardFindResult_t> *steamCallResult = (*it);
                delete steamCallResult;
                findLeaderboardVector.erase(it);
            } else {
                it++;
            }
        }

        for (auto it =
                uploadScoreVector.begin(); it != uploadScoreVector.end();) {
            if ((*it)->IsActive() == 0 || force) {
                CCallResult<SteamCallbackClass, LeaderboardScoreUploaded_t> *steamCallResult = (*it);
                delete steamCallResult;
                uploadScoreVector.erase(it);
            } else {
                ++it;
            }
        }
    }

    void SteamCallbackClass::throwException(JNIEnv *env, const char *str) {
        jclass MyOwnException = env->FindClass("steam/exception/SteamException");
        if (MyOwnException != nullptr) {
            env->ThrowNew(MyOwnException, str);
        } else {
            std::cerr << "Failed to find exception steam/exception/SteamException: " << str;
        }
    }

    void SteamCallbackClass::log(const char *theString) {
        log(this->jvm, theString);
    }

    void SteamCallbackClass::log(JavaVM *jvm, const char *theString) {
        JNIEnv *env;
        jvm->AttachCurrentThread((void **) &env, nullptr);
        log(env, theString);
        jvm->DetachCurrentThread();
    }

    void SteamCallbackClass::log(JNIEnv *env, const char *theString) {
        jclass javaClass = env->FindClass("steam/steam_api");
        if (javaClass == nullptr) {
            std::cerr << "NATIVE: Failed to find steam/steam_api" << endl;
            std::cerr << theString << endl;
            return;
        }

        jmethodID callBackMethodID = env->GetStaticMethodID(javaClass, "logFromNative", "(Ljava/lang/String;)V");
        if (callBackMethodID == nullptr) {
            std::cerr << "NATIVE: Could not find java method logFromNative(Ljava/lang/String;)V" << std::endl;
            std::cerr << theString << endl;
            return;
        }

        jstring javaString = env->NewStringUTF(theString);
        env->CallStaticObjectMethod(javaClass, callBackMethodID, javaString);
    }

    void SteamCallbackClass::OnUserStatsReceived(UserStatsReceived_t *pCallback) {
        if (pCallback != nullptr) {
            CSteamID userId = pCallback->m_steamIDUser;
            ostringstream nativeStringBuffer;
            nativeStringBuffer << "UserStatsReceived_t result=" << (int) pCallback->m_eResult << " gameid="
                               << pCallback->m_nGameID << " userId=" << userId.GetAccountID();
            log(&nativeStringBuffer.str()[0]);

        } else {
            log("OnUserStatsReceived was passed null");
        }
    }

    void SteamCallbackClass::OnUserStatsStored(UserStatsStored_t *pCallback) {
        if (pCallback != nullptr) {
            ostringstream nativeStringBuffer;
            nativeStringBuffer << "UserStatsStored_t result=" << (int) pCallback->m_eResult << " gameid="
                               << (int) pCallback->m_nGameID;
            log(&nativeStringBuffer.str()[0]);
        } else {
            log("OnUserStatsStored was passed null");
        }
    }

    void SteamCallbackClass::OnAchievementStored(UserAchievementStored_t *pCallback) {
        if (pCallback != nullptr) {
            ostringstream nativeStringBuffer;
            nativeStringBuffer << "UserAchievementStored_t name=" << pCallback->m_rgchAchievementName;
            log(&nativeStringBuffer.str()[0]);
        } else {
            log("OnAchievementStored was passed null");
        }
    }

    class JVMCleanup {
    public:
        JavaVM *jvm;

        explicit JVMCleanup(JavaVM *jvm) {
            this->jvm = jvm;
        }

        ~JVMCleanup() {
            jvm->DetachCurrentThread();
        }
    };

    void SteamCallbackClass::OnLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t *pParam, bool ioFailure) {
        if (pParam == nullptr) {
            log("OnLeaderboardScoresDownloaded was passed null");
            return;
        }
        JNIEnv *env;
        jvm->AttachCurrentThread((void **) &env, nullptr);
        JVMCleanup cleanupClass(jvm);

        jclass leaderboardScoresDownloadedClass = env->FindClass("steam/LeaderboardScoresDownloaded_t");
        if (leaderboardScoresDownloadedClass == nullptr) {
            log("OnLeaderboardScoresDownloaded couldn't find steam/LeaderboardScoresDownloaded_t");
            return;
        }
        jmethodID leaderboardConstructorMethod = env->GetMethodID(leaderboardScoresDownloadedClass, "<init>", "(JJI)V");
        if (leaderboardConstructorMethod == nullptr) {
            log("OnLeaderboardScoresDownloaded couldn't find <init>(JJI)V");
            return;
        }
        jobject leaderboardScoresDownloaded = env->NewObject(leaderboardScoresDownloadedClass,
                                                             leaderboardConstructorMethod,
                                                             pParam->m_hSteamLeaderboard,
                                                             pParam->m_hSteamLeaderboardEntries, pParam->m_cEntryCount);
        if (leaderboardScoresDownloaded == nullptr) {
            log("OnLeaderboardScoresDownloaded couldn't create new LeaderboardScoresDownloaded_t(JJJ)V");
            return;
        }

        jclass iSteamUserStatsClass = env->FindClass("steam/ISteamUserStats");
        if (iSteamUserStatsClass == nullptr) {
            log("OnLeaderboardScoresDownloaded couldn't find steam/ISteamUserStats");
            return;
        }
        jmethodID onLeaderboardScoredDownloadedMethodID = env->GetStaticMethodID(iSteamUserStatsClass,
                                                                                 "OnLeaderboardScoresDownloaded",
                                                                                 "(Lsteam/LeaderboardScoresDownloaded_t;Z)V");
        if (onLeaderboardScoredDownloadedMethodID == nullptr) {
            log(
                    "OnLeaderboardScoresDownloaded couldn't find method OnLeaderboardScoresDownloaded(Lsteam/LeaderboardScoresDownloaded_t;Z)V");
            return;
        }
        env->CallStaticVoidMethod(iSteamUserStatsClass, onLeaderboardScoredDownloadedMethodID,
                                  leaderboardScoresDownloaded,
                                  ioFailure);
    }

    void SteamCallbackClass::OnFindLeaderboard(LeaderboardFindResult_t *pFindLeaderboardResult, bool bIOFailure) {
        if (pFindLeaderboardResult == nullptr) {
            log("OnFindLeaderboard was passed null");
            return;
        }
        JNIEnv *env;
        jvm->AttachCurrentThread((void **) &env, nullptr);
        JVMCleanup cleanupClass(jvm);

        jclass leaderboardFindResultClass = env->FindClass("steam/LeaderboardFindResult_t");
        if (leaderboardFindResultClass == nullptr) {
            log("OnFindLeaderboard couldn't find steam/LeaderboardFindResult_t");
            return;
        }

        jmethodID leaderboardConstructorMethod = env->GetMethodID(leaderboardFindResultClass, "<init>", "(JI)V");
        if (leaderboardConstructorMethod == nullptr) {
            log("OnFindLeaderboard couldn't find <init>(JI)V");
            return;
        }
        jobject leaderboardFindResult = env->NewObject(leaderboardFindResultClass, leaderboardConstructorMethod,
                                                       pFindLeaderboardResult->m_hSteamLeaderboard,
                                                       pFindLeaderboardResult->m_bLeaderboardFound);
        if (leaderboardFindResult == nullptr) {
            log("OnFindLeaderboard couldn't create new LeaderboardFindResult_t(JI)V");
            return;
        }

        jclass iSteamUserStatsClass = env->FindClass("steam/ISteamUserStats");
        if (iSteamUserStatsClass == nullptr) {
            log("OnFindLeaderboard couldn't find steam/ISteamUserStats");
            return;
        }
        jmethodID onFindLeaderboardMethodID = env->GetStaticMethodID(iSteamUserStatsClass, "OnFindLeaderboard",
                                                                     "(Lsteam/LeaderboardFindResult_t;Z)V");
        if (onFindLeaderboardMethodID == nullptr) {
            log("OnFindLeaderboard couldn't find method OnFindLeaderboard(Lsteam/LeaderboardFindResult_t;Z)V");
            return;
        }
        env->CallStaticVoidMethod(iSteamUserStatsClass, onFindLeaderboardMethodID, leaderboardFindResult, bIOFailure);
    }

    void SteamCallbackClass::OnUploadScore(LeaderboardScoreUploaded_t *pScoreUploadedResult, bool bIOFailure) {
        if (pScoreUploadedResult == nullptr) {
            log("OnUploadScore was passed null");
            return;
        }
        JNIEnv *env;
        jvm->AttachCurrentThread((void **) &env, nullptr);
        JVMCleanup cleanupClass(jvm);

        jclass leaderboardScoreUploadedClass = env->FindClass("steam/LeaderboardScoreUploaded_t");
        if (leaderboardScoreUploadedClass == nullptr) {
            log("OnUploadScore couldn't find steam/LeaderboardScoreUploaded_t");
            return;
        }
        jmethodID scoreUploadedConstructorMethod = env->GetMethodID(leaderboardScoreUploadedClass, "<init>",
                                                                    "(IJIIII)V");
        if (scoreUploadedConstructorMethod == nullptr) {
            log("OnUploadScore couldn't find <init>(IJIIII)V");
            return;
        }
        jobject leaderboardScoreUploaded = env->NewObject(leaderboardScoreUploadedClass, scoreUploadedConstructorMethod,
                                                          pScoreUploadedResult->m_bSuccess,
                                                          pScoreUploadedResult->m_hSteamLeaderboard,
                                                          pScoreUploadedResult->m_nScore,
                                                          pScoreUploadedResult->m_bScoreChanged,
                                                          pScoreUploadedResult->m_nGlobalRankNew,
                                                          pScoreUploadedResult->m_nGlobalRankPrevious);
        if (leaderboardScoreUploaded == nullptr) {
            log("OnUploadScore couldn't create new LeaderboardScoreUploaded_t(IJIIII)V");
            return;
        }

        jclass iSteamUserStatsClass = env->FindClass("steam/ISteamUserStats");
        if (iSteamUserStatsClass == nullptr) {
            log("OnUploadScore couldn't find steam/ISteamUserStats");
            return;
        }
        jmethodID onFindLeaderboardMethodID = env->GetStaticMethodID(iSteamUserStatsClass, "OnUploadScore",
                                                                     "(Lsteam/LeaderboardScoreUploaded_t;Z)V");
        if (onFindLeaderboardMethodID == nullptr) {
            log("OnUploadScore couldn't find method OnUploadScore(Lsteam/LeaderboardScoreUploaded_t;Z)V");
            return;
        }
        env->CallStaticVoidMethod(iSteamUserStatsClass, onFindLeaderboardMethodID, leaderboardScoreUploaded,
                                  bIOFailure);
    }

}
