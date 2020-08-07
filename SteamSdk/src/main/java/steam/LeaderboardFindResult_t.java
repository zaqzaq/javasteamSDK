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
