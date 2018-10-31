/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class SteamDlcInfo {
	public final long appId;
	public final boolean available;
	public final String name;

	public SteamDlcInfo(long appId, boolean available, String name) {
		super();
		this.appId = appId;
		this.available = available;
		this.name = name;
	}

	public long getAppId() {
		return appId;
	}

	public boolean isAvailable() {
		return available;
	}

	public String getName() {
		return name;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (appId ^ (appId >>> 32));
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SteamDlcInfo other = (SteamDlcInfo) obj;
		if (appId != other.appId) {
			return false;
		}
		if (available != other.available) {
			return false;
		}
      if (name == null) {
         return other.name == null;
      } else { return name.equals(other.name); }
   }
}
