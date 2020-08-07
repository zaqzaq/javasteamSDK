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
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class ISteamApps {
	public static boolean isSubscribed(){
		return nBIsSubscribed();
	}
	
	private static native boolean nBIsSubscribed();
	
	public static boolean isLowViolence(){
		return nBIsLowViolence();
	}
	
	private static native boolean nBIsLowViolence();
	
	public static boolean isCyberCafe(){
		return nBIsCyberCafe();
	}
	
	private static native boolean nBIsCyberCafe();
	
	public static boolean isVACBanned(){
		return nBIsVACBanned();
	}
	
	private static native boolean nBIsVACBanned();
	

	public static String getCurrentGameLanguage(){
		return nGetCurrentGameLanguage();
	}
	private static native String nGetCurrentGameLanguage();
	
	public static String getAvailableGameLanguages(){
		return nGetAvailableGameLanguages();
	}
	private static native String nGetAvailableGameLanguages();

	/**only use this member if you need to check ownership of another game related to yours, a demo for example */
	public static boolean isSubscribedApp(long appId){
		return nBIsSubscribedApp(appId);
	}
	public static native boolean nBIsSubscribedApp(long appId);

	/** Takes AppID of DLC and checks if the user owns the DLC & if the DLC is installed*/
	public static boolean isDlcInstalled(long appId){
		return nBIsDlcInstalled(appId);
	}
	public static native boolean nBIsDlcInstalled(long appId);

	/** returns the Unix time of the purchase of the app*/
	public static long getEarliestPurchaseUnixTime(long appId){
		return nGetEarliestPurchaseUnixTime(appId);
	}
	public static native long nGetEarliestPurchaseUnixTime(long appId);

	/** Checks if the user is subscribed to the current app through a free weekend
	 This function will return false for users who have a retail or other type of license
	 Before using, please ask your Valve technical contact how to package and secure your free weekened */
	public static boolean isSubscribedFromFreeWeekend(){
		return nBIsSubscribedFromFreeWeekend();
	}
	public static native boolean nBIsSubscribedFromFreeWeekend();

	/** Returns the number of DLC pieces for the running app */
	public static int getDLCCount(){
		return nGetDLCCount();
	}
	public static native int nGetDLCCount();

	/** Returns metadata for DLC by index, of range [0, GetDLCCount()] */
	public static SteamDlcInfo getDLCDataByIndex(int iDLC){
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
		String name = new String(nameBytes, StandardCharsets.UTF_8);

		return new SteamDlcInfo(appId, availableByte != 0, name);
	}
	private static native void nGetDLCDataByIndex(int iDLC, ByteBuffer buffer);

	// Install/Uninstall control for optional DLC
	public static void installDlc(long appId){
		nInstallDLC(appId);
	}
	private static native void nInstallDLC(long appId);
	
	public static void uninstallDlc(long appId){
		nUninstallDLC(appId);
	}
	private static native void nUninstallDLC(long appId);
	
	// Request cd-key for yourself or owned DLC. If you are interested in this
	// data then make sure you provide us with a list of valid keys to be distributed
	// to users when they purchase the game, before the game ships.
	// You'll receive an AppProofOfPurchaseKeyResponse_t callback when
	// the key is available (which may be immediately).
//	TODO virtual void RequestAppProofOfPurchaseKey( AppId_t nAppID ) = 0;

	/** returns current beta branch name, 'public' is the default branch */
	public static String getCurrentBetaName(){
		return nGetCurrentBetaName();
	}
	private static native String nGetCurrentBetaName();
	
	/** signal Steam that game files seems corrupt or missing */
	public static boolean markContentCorrupt(boolean missingFilesOnly){
		return nMarkContentCorrupt(missingFilesOnly);
	}
	private static native boolean nMarkContentCorrupt(boolean bMissingFilesOnly );
	
	/** return installed depots in mount order */
	public static long[] getInstalledDepots(long appId){
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
	private static native int nGetInstalledDepots(long appId, ByteBuffer buffer);

	/** returns current app install folder for AppID, returns folder name length */
	public static String getAppInstallDir(long appId){
		return nGetAppInstallDir(appId);
	}
	private static native String nGetAppInstallDir(long appId);
	
	/** returns true if that app is installed (not necessarily owned) */
	public static boolean isAppInstalled(long appId){
		return nBIsAppInstalled(appId);
	}
	private static native boolean nBIsAppInstalled(long appId);
	
	/** returns the SteamID of the original owner. If different from current user, it's borrowed */
	public static CSteamID getAppOwner(){
		return new CSteamID(nGetAppOwner());
	}
	private static native long nGetAppOwner();

	// Returns the associated launch param if the game is run via steam://run/<appid>//?param1=value1;param2=value2;param3=value3 etc.
	// Parameter names starting with the character '@' are reserved for internal use and will always return and empty string.
	// Parameter names starting with an underscore '_' are reserved for steam features -- they can be queried by the game,
	// but it is advised that you not param names beginning with an underscore for your own features.
//	TODO virtual const char *GetLaunchQueryParam( const char *pchKey ) = 0;

	// get download progress for optional DLC
//	TODO virtual bool GetDlcDownloadProgress( AppId_t nAppID, uint64 *punBytesDownloaded, uint64 *punBytesTotal ) = 0; 

	/** return the buildid of this app, may change at any time based on backend updates to the game */
	public static int getAppBuildId(){
		return nGetAppBuildId();
	}
	private static native int nGetAppBuildId();
}
