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
