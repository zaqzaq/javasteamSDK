/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public enum ELeaderboardSortMethod {
		k_ELeaderboardSortMethodNone,// = 0,
		k_ELeaderboardSortMethodAscending,// = 1,	// top-score is lowest number
		k_ELeaderboardSortMethodDescending;// = 2,	// top-score is highest number
		
		public static ELeaderboardSortMethod fromInt(int theInt){
			return values()[theInt];
		}

		public int toInt() {
			return ordinal();
		}
}
