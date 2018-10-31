/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class LeaderboardFindResult_t {
	/**
	 * handle to the leaderboard serarched for, 0 if no leaderboard found
	 */
	private SteamLeaderboard_t m_hSteamLeaderboard;
	/**
	 * 0 if no leaderboard found
	 */
	private int m_bLeaderboardFound;
	
	public LeaderboardFindResult_t() {
		super();
	}

	public LeaderboardFindResult_t(long m_hSteamLeaderboard, int m_bLeaderboardFound) {
		super();
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
		this.m_bLeaderboardFound = m_bLeaderboardFound;
	}

	public SteamLeaderboard_t getM_hSteamLeaderboard() {
		return m_hSteamLeaderboard;
	}

	public void setM_hSteamLeaderboard(long m_hSteamLeaderboard) {
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
	}

	public int getM_bLeaderboardFound() {
		return m_bLeaderboardFound;
	}

	public void setM_bLeaderboardFound(int m_bLeaderboardFound) {
		this.m_bLeaderboardFound = m_bLeaderboardFound;
	}

	public String toString() {
		return "LeaderboardFindResult_t [m_hSteamLeaderboard=" + m_hSteamLeaderboard + ", m_bLeaderboardFound=" + m_bLeaderboardFound + "]";
	}
	
	
}
