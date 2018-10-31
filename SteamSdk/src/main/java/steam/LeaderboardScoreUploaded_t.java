/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class LeaderboardScoreUploaded_t {
	/**
	 * 1 if the call was successful
	 */
	private int m_bSuccess;
	
	/**
	 * the leaderboard handle that was
	 */
	private SteamLeaderboard_t m_hSteamLeaderboard;
	
	/**
	 * the score that was attempted to set
	 */
	private int m_nScore;
	
	/**
	 * true if the score in the leaderboard change, false if the existing score was better
	 */
	private int m_bScoreChanged;
	
	/**
	 * the new global rank of the user in this leaderboard
	 */
	private int m_nGlobalRankNew;
	
	/**
	 * the previous global rank of the user in this leaderboard; 0 if the user had no existing entry in the leaderboard
	 */
	private int m_nGlobalRankPrevious;
	
	public LeaderboardScoreUploaded_t() {
		super();
	}
	
	public LeaderboardScoreUploaded_t(int m_bSuccess, long m_hSteamLeaderboard, int m_nScore, int m_bScoreChanged, int m_nGlobalRankNew, int m_nGlobalRankPrevious) {
		super();
		this.m_bSuccess = m_bSuccess;
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
		this.m_nScore = m_nScore;
		this.m_bScoreChanged = m_bScoreChanged;
		this.m_nGlobalRankNew = m_nGlobalRankNew;
		this.m_nGlobalRankPrevious = m_nGlobalRankPrevious;
	}

	/**
	 * 1 if the call was successful
	 */
	public int getM_bSuccess() {
		return m_bSuccess;
	}

	public void setM_bSuccess(int m_bSuccess) {
		this.m_bSuccess = m_bSuccess;
	}

	/**
	 * the leaderboard handle that was
	 */
	public SteamLeaderboard_t getM_hSteamLeaderboard() {
		return m_hSteamLeaderboard;
	}

	public void setM_hSteamLeaderboard(long m_hSteamLeaderboard) {
		this.m_hSteamLeaderboard = new SteamLeaderboard_t(m_hSteamLeaderboard);
	}

	/**
	 * the score that was attempted to set
	 */
	public int getM_nScore() {
		return m_nScore;
	}

	public void setM_nScore(int m_nScore) {
		this.m_nScore = m_nScore;
	}

	/**
	 * true if the score in the leaderboard change, false if the existing score was better
	 */
	public int getM_bScoreChanged() {
		return m_bScoreChanged;
	}

	public void setM_bScoreChanged(int m_bScoreChanged) {
		this.m_bScoreChanged = m_bScoreChanged;
	}

	/**
	 * the new global rank of the user in this leaderboard
	 */
	public int getM_nGlobalRankNew() {
		return m_nGlobalRankNew;
	}

	public void setM_nGlobalRankNew(int m_nGlobalRankNew) {
		this.m_nGlobalRankNew = m_nGlobalRankNew;
	}

	/**
	 * the previous global rank of the user in this leaderboard; 0 if the user had no existing entry in the leaderboard
	 */
	public int getM_nGlobalRankPrevious() {
		return m_nGlobalRankPrevious;
	}

	public void setM_nGlobalRankPrevious(int m_nGlobalRankPrevious) {
		this.m_nGlobalRankPrevious = m_nGlobalRankPrevious;
	}
}
