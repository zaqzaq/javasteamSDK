/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public abstract class SteamUserStatsListener {
	private long cppCallbackInstance;

	/**
	 * Call destroy when you're done with this object otherwise it will never get garbage collected
	 */
	public SteamUserStatsListener() {
		super();

		cppCallbackInstance = nInitializeCppCallback();
	}

	public void destroy() {
		if (cppCallbackInstance != 0) {
			nFreeCppCallback(cppCallbackInstance);
		}
		cppCallbackInstance = 0;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		destroy();
	}

	public long getCppCallbackInstance() {
		return cppCallbackInstance;
	}

	private native long nInitializeCppCallback();

	private native void nFreeCppCallback(long cppCallbackInstance);

	public abstract void onLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t pParam, boolean ioFailure);

	public abstract void onFindLeaderboard(LeaderboardFindResult_t pFindLeaderboardResult, boolean bIOFailure);

	public abstract void onUploadScore(LeaderboardScoreUploaded_t pScoreUploadedResult, boolean bIOFailure);
}
