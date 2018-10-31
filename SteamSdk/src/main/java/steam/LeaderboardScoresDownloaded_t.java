/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class LeaderboardScoresDownloaded_t {
	/**
	 * handle to the leaderboard
	 */
	private SteamLeaderboard_t m_hSteamLeaderboard;
	
	/**
	 * the handle to pass into GetDownloadedLeaderboardEntries()
	 */
	private SteamLeaderboardEntries_t m_hSteamLeaderboardEntries;
	
	/**
	 * the number of entries downloaded
	 */
	private int m_cEntryCount;

	public LeaderboardScoresDownloaded_t() {
		super();
	}

	public LeaderboardScoresDownloaded_t(long m_hSteamLeaderboard, long m_hSteamLeaderboardEntries, int m_cEntryCount) {
		super();
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
		this.m_hSteamLeaderboardEntries = new SteamLeaderboardEntries_t(m_hSteamLeaderboardEntries);
		this.m_cEntryCount = m_cEntryCount;
	}

	public SteamLeaderboard_t getM_hSteamLeaderboard() {
		return m_hSteamLeaderboard;
	}

	public void setM_hSteamLeaderboard(long m_hSteamLeaderboard) {
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
	}

	public SteamLeaderboardEntries_t getM_hSteamLeaderboardEntries() {
		return m_hSteamLeaderboardEntries;
	}

	public void setM_hSteamLeaderboardEntries(long m_hSteamLeaderboardEntries) {
		this.m_hSteamLeaderboardEntries = new SteamLeaderboardEntries_t(m_hSteamLeaderboardEntries);
	}

	public int getM_cEntryCount() {
		return m_cEntryCount;
	}

	public void setM_cEntryCount(int m_cEntryCount) {
		this.m_cEntryCount = m_cEntryCount;
	}
}
