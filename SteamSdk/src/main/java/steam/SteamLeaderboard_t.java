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

public class SteamLeaderboard_t {
	public long steamLeaderboard_t;
	
	public SteamLeaderboard_t() {
		super();
	}

	public SteamLeaderboard_t(long steamLeaderboard_t) {
		super();
		this.steamLeaderboard_t = steamLeaderboard_t;
	}

	public String toString() {
		return "SteamLeaderboard_t [steamLeaderboard_t=" + steamLeaderboard_t + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (steamLeaderboard_t ^ (steamLeaderboard_t >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SteamLeaderboard_t other = (SteamLeaderboard_t) obj;
      return steamLeaderboard_t == other.steamLeaderboard_t;
   }
	
	
}
