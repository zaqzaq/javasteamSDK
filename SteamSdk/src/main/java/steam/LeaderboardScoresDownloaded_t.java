/*
 * Copyright 2020 Nimbly Games, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
