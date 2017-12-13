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
		if (steamLeaderboard_t != other.steamLeaderboard_t)
			return false;
		return true;
	}
	
	
}
