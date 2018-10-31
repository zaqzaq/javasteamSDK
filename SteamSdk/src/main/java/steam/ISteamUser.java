/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

import java.nio.ByteBuffer;

public class ISteamUser {
	public static final CSteamID GetSteamID() {
		return new CSteamID(nGetSteamID());
	}

	private static final native long nGetSteamID();
	
	public static final int InitiateGameConnection(ByteBuffer pAuthBlob, CSteamID steamIDGameServer, int unIPServer, int usPortServer, boolean bSecure){
		return nInitiateGameConnection(pAuthBlob, steamIDGameServer.uint64, unIPServer, usPortServer, bSecure);
	}
	private static final native int nInitiateGameConnection(ByteBuffer pAuthBlob, long steamIDGameServer, int unIPServer, int usPortServer, boolean bSecure);
	
	public static final native void TerminateGameConnection(int unIPServer, int usPortServer);
}
