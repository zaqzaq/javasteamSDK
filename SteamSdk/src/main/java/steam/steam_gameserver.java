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


public class steam_gameserver {
	public static boolean SteamGameServer_Init(int unIP, int usPort, int usGamePort, int usSpectatorPort, int usQueryPort, EServerMode eServerMode, String pchGameDir, String pchVersionString ){
		return nSteamGameServer_Init(unIP, usPort, usGamePort, usSpectatorPort, usQueryPort, eServerMode.toInt, pchGameDir, pchVersionString);
	}
	
	private native static boolean nSteamGameServer_Init(int unIP, int usPort, int usGamePort, int usSpectatorPort, int usQueryPort, int eServerMode, String pchGameDir, String pchVersionString );
	
	public static final native void SteamGameServer_Shutdown();
	
	public static final native void SteamGameServer_RunCallbacks();
	
	public static final native boolean SteamGameServer_BSecure();
	
	public static final native long SteamGameServer_GetSteamID();
}
