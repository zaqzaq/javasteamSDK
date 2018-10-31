/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ISteamApps {
	public static final boolean isSubscribed(){
		return nBIsSubscribed();
	}
	
	private static final native boolean nBIsSubscribed();
	
	public static final boolean isLowViolence(){
		return nBIsLowViolence();
	}
	
	private static final native boolean nBIsLowViolence();
	
	public static final boolean isCyberCafe(){
		return nBIsCyberCafe();
	}
	
	private static final native boolean nBIsCyberCafe();
	
	public static final boolean isVACBanned(){
		return nBIsVACBanned();
	}
	
	private static final native boolean nBIsVACBanned();
	

	public static final String getCurrentGameLanuaged(){
		return nGetCurrentGameLanguage();
	}
	private static final native String nGetCurrentGameLanguage();
	
	public static final String getAvailableGameLanguages(){
		return nGetAvailableGameLanguages();
	}
	private static final native String nGetAvailableGameLanguages();

	/**only use this member if you need to check ownership of another game related to yours, a demo for example */
	public static final boolean isSubscribedApp(long appId){
		return nBIsSubscribedApp(appId);
	}
	public static final native boolean nBIsSubscribedApp(long appId);

	/** Takes AppID of DLC and checks if the user owns the DLC & if the DLC is installed*/
	public static final boolean isDlcInstallced(long appId){
		return nBIsDlcInstalled(appId);
	}
	public static final native boolean nBIsDlcInstalled(long appId);

	/** returns the Unix time of the purchase of the app*/
	public static final long getEarliestPurchaseUnixTime(long appId){
		return nGetEarliestPurchaseUnixTime(appId);
	}
	public static final native long nGetEarliestPurchaseUnixTime(long appId);

	/** Checks if the user is subscribed to the current app through a free weekend
	 This function will return false for users who have a retail or other type of license
	 Before using, please ask your Valve technical contact how to package and secure your free weekened */
	public static final boolean isSubscribedFromFreeWeekend(){
		return nBIsSubscribedFromFreeWeekend();
	}
	public static final native boolean nBIsSubscribedFromFreeWeekend();

	/** Returns the number of DLC pieces for the running app */
	public static final int getDLCCount(){
		return nGetDLCCount();
	}
	public static final native int nGetDLCCount();

	/** Returns metadata for DLC by index, of range [0, GetDLCCount()] */
	public static final SteamDlcInfo getDLCDataByIndex(int iDLC){
		// app id 4 bytes
		// available boolean 1 byte
		// name 128 bytes
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 + 1 + 128).order(ByteOrder.nativeOrder());
		nGetDLCDataByIndex(iDLC, buffer);
		buffer.position(0);
		buffer.limit(buffer.capacity());
		long appId = buffer.getInt();
		byte availableByte = buffer.get();
		byte[] nameBytes = new byte[128];
		buffer.get(nameBytes);
		String name = new String(nameBytes, Charset.forName("UTF-8"));
		
		return new SteamDlcInfo(appId, availableByte != 0, name);
	}
	private static final native void nGetDLCDataByIndex(int iDLC, ByteBuffer buffer);

	// Install/Uninstall control for optional DLC
	public static final void installDlc(long appId){
		nInstallDLC(appId);
	}
	private static final native void nInstallDLC(long appId);
	
	public static final void uninstallDlc(long appId){
		nUninstallDLC(appId);
	}
	private static final native void nUninstallDLC(long appId);
	
	// Request cd-key for yourself or owned DLC. If you are interested in this
	// data then make sure you provide us with a list of valid keys to be distributed
	// to users when they purchase the game, before the game ships.
	// You'll receive an AppProofOfPurchaseKeyResponse_t callback when
	// the key is available (which may be immediately).
//	TODO virtual void RequestAppProofOfPurchaseKey( AppId_t nAppID ) = 0;

	/** returns current beta branch name, 'public' is the default branch */
	public static final String getCurrentBetaName(){
		return nGetCurrentBetaName();
	}
	private static final native String nGetCurrentBetaName();
	
	/** signal Steam that game files seems corrupt or missing */
	public static final boolean markContentCorrupt(boolean missingFilesOnly){
		return nMarkContentCorrupt(missingFilesOnly);
	}
	private static final native boolean nMarkContentCorrupt( boolean bMissingFilesOnly );
	
	/** return installed depots in mount order */
	public static final long[] getInstalledDepots(long appId){
		final int depotCount = 16;
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * depotCount).order(ByteOrder.nativeOrder());
		int count = nGetInstalledDepots(appId, buffer);
		buffer.reset();
		long[] depots = new long[count];
		for(int i = 0; i < depots.length; i++){
			depots[i] = buffer.getInt();
		}
		return depots;
	}
	private static final native int nGetInstalledDepots(long appId, ByteBuffer buffer);

	/** returns current app install folder for AppID, returns folder name length */
	public static final String getAppInstallDir(long appId){
		return nGetAppInstallDir(appId);
	}
	private static final native String nGetAppInstallDir(long appId);
	
	/** returns true if that app is installed (not necessarily owned) */
	public static final boolean isAppInstalled(long appId){
		return nBIsAppInstalled(appId);
	}
	private static final native boolean nBIsAppInstalled(long appId);
	
	/** returns the SteamID of the original owner. If different from current user, it's borrowed */
	public static final CSteamID getAppOwner(){
		return new CSteamID(nGetAppOwner());
	}
	private static final native long nGetAppOwner();

	// Returns the associated launch param if the game is run via steam://run/<appid>//?param1=value1;param2=value2;param3=value3 etc.
	// Parameter names starting with the character '@' are reserved for internal use and will always return and empty string.
	// Parameter names starting with an underscore '_' are reserved for steam features -- they can be queried by the game,
	// but it is advised that you not param names beginning with an underscore for your own features.
//	TODO virtual const char *GetLaunchQueryParam( const char *pchKey ) = 0;

	// get download progress for optional DLC
//	TODO virtual bool GetDlcDownloadProgress( AppId_t nAppID, uint64 *punBytesDownloaded, uint64 *punBytesTotal ) = 0; 

	/** return the buildid of this app, may change at any time based on backend updates to the game */
	public static final int getAppBuildId(){
		return nGetAppBuildId();
	}
	private static final native int nGetAppBuildId();
}
