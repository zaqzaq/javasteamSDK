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

import java.util.HashMap;

public enum EDenyReason {
	k_EDenyInvalid(0),
	k_EDenyInvalidVersion(1),
	k_EDenyGeneric(2),
	k_EDenyNotLoggedOn(3),
	k_EDenyNoLicense(4),
	k_EDenyCheater(5),
	k_EDenyLoggedInElseWhere(6),
	k_EDenyUnknownText(7),
	k_EDenyIncompatibleAnticheat(8),
	k_EDenyMemoryCorruption(9),
	k_EDenyIncompatibleSoftware(10),
	k_EDenySteamConnectionLost(11),
	k_EDenySteamConnectionError(12),
	k_EDenySteamResponseTimedOut(13),
	k_EDenySteamValidationStalled(14),
	k_EDenySteamOwnerLeftGuestUser(15);
	public static final HashMap<Integer, EDenyReason> FROM_INT_VALUE = new HashMap<Integer, EDenyReason>();
	static{
		EDenyReason[] values = EDenyReason.values();
		for(int i = 0; i < values.length; i++){
			FROM_INT_VALUE.put(Integer.valueOf(values[i].intValue), values[i]);
		}
	}
	public final int intValue;

   EDenyReason(int intValue) {
		this.intValue = intValue;
	}
}
