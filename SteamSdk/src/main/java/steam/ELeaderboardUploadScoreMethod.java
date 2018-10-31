/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public enum ELeaderboardUploadScoreMethod {
	k_ELeaderboardUploadScoreMethodNone,
	/**
	 * Leaderboard will keep user's best score
	 */
	k_ELeaderboardUploadScoreMethodKeepBest,
	/**
	 *  Leaderboard will always replace score with specified
	 */
	k_ELeaderboardUploadScoreMethodForceUpdate;
	
	public int toInt(){
		return ordinal();
	}
	
	public static ELeaderboardUploadScoreMethod fromInt(int theInt){
		return values()[theInt];
	}
}
