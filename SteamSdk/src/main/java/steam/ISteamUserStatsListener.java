/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public interface ISteamUserStatsListener {
   void OnLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t pParam, boolean ioFailure);

   void OnFindLeaderboard(LeaderboardFindResult_t pFindLeaderboardResult, boolean bIOFailure);

   void OnUploadScore(LeaderboardScoreUploaded_t pScoreUploadedResult, boolean bIOFailure);
}
