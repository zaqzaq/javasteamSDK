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

public class LeaderboardEntry_t {
	/**
	 * user with the entry - use SteamFriends()->GetFriendPersonaName() & SteamFriends()->GetFriendAvatar() to get more info
	 */
	private CSteamID m_steamIDUser;
	/**
	 * [1..N], where N is the number of users with an entry in the leaderboard
	 */
	private int m_nGlobalRank;
	/**
	 * score as set in the leaderboard
	 */
	private int m_nScore;
	/**
	 * number of int32 details available for this entry
	 */
	private int m_cDetails;
	
	private long m_hUGC;
	
	public LeaderboardEntry_t() {
		super();
	}

	public CSteamID getM_steamIDUser() {
		return m_steamIDUser;
	}

	public void setM_steamIDUser(CSteamID m_steamIDUser) {
		this.m_steamIDUser = m_steamIDUser;
	}

	public int getM_nGlobalRank() {
		return m_nGlobalRank;
	}

	public void setM_nGlobalRank(int m_nGlobalRank) {
		this.m_nGlobalRank = m_nGlobalRank;
	}

	public int getM_nScore() {
		return m_nScore;
	}

	public void setM_nScore(int m_nScore) {
		this.m_nScore = m_nScore;
	}

	public int getM_cDetails() {
		return m_cDetails;
	}

	public void setM_cDetails(int m_cDetails) {
		this.m_cDetails = m_cDetails;
	}

	public long getM_hUGC() {
		return m_hUGC;
	}

	public void setM_hUGC(long m_hUGC) {
		this.m_hUGC = m_hUGC;
	}
}
