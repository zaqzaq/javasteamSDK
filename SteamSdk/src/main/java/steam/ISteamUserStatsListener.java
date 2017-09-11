package steam;

public interface ISteamUserStatsListener {
	public void OnLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t pParam, boolean ioFailure);

	public void OnFindLeaderboard(LeaderboardFindResult_t pFindLeaderboardResult, boolean bIOFailure);

	public void OnUploadScore(LeaderboardScoreUploaded_t pScoreUploadedResult, boolean bIOFailure);
}
