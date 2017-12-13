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
	private EDenyReason(int intValue){
		this.intValue = intValue;
	}
}
