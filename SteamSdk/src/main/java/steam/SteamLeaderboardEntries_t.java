/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class SteamLeaderboardEntries_t {
	public long steamLeaderboardEntries;

	
	public SteamLeaderboardEntries_t() {
		super();
	}

	public SteamLeaderboardEntries_t(long steamLeaderboardEntries) {
		super();
		this.steamLeaderboardEntries = steamLeaderboardEntries;
	}

	public String toString() {
		return "SteamLeaderboardEntries_t [steamLeaderboardEntries=" + steamLeaderboardEntries + "]";
	}
}
