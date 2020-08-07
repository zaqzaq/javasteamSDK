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
