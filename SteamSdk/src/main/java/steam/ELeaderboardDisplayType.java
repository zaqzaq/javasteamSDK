/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public enum ELeaderboardDisplayType {
	k_ELeaderboardDisplayTypeNone,
	/**
	 * simple numerical score
	 */
	k_ELeaderboardDisplayTypeNumeric,
	/**
	 * the score represents a time, in seconds
	 */
	k_ELeaderboardDisplayTypeTimeSeconds,
	k_ELeaderboardDisplayTypeTimeMilliSeconds;
	
	public int toInt(){
		return ordinal();
	}
	
	public static ELeaderboardDisplayType fromInt(int theInt){
		return values()[theInt];
	}
}
