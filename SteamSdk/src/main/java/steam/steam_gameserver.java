/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
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
