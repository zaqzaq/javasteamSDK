/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public enum ELeaderboardDataRequest {
	k_ELeaderboardDataRequestGlobal,
	k_ELeaderboardDataRequestGlobalAroundUser,
	k_ELeaderboardDataRequestFriends,
	k_ELeaderboardDataRequestUsers;
	
	public static ELeaderboardDataRequest fromInt(int theInt){
		return values()[theInt];
	}
	
	public int toInt(){
		return ordinal();
	}
}
