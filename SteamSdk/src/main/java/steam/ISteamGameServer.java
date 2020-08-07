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

import java.util.ArrayList;

public class ISteamGameServer {
	public static final native void LogOn();
	public static final native void LogOff();
	public static final native boolean BLoggedOn();
	public static final native boolean BSecure();
	public static final native int GetPublicIP();
	
	public static final CSteamID GetSteamID(){
		return new CSteamID(nGetSteamID());
	}
	public static final native long nGetSteamID();
	
	public static final boolean SendUserConnectAndAuthenticate(int unIPClient, byte[] pvAuthBlob, CSteamID pSteamIDUser){
		return nSendUserConnectAndAuthenticate(unIPClient, pvAuthBlob, pSteamIDUser.uint64);
	}
	private static final native boolean nSendUserConnectAndAuthenticate(int unIPClient, byte[] pvAuthBlob, long pSteamIDUser);
	
	public static final void SendUserDisconnect( CSteamID steamIDUser ){
		nSendUserDisconnect( steamIDUser.uint64 );
	}
	private static final native void nSendUserDisconnect(long steamIDUserLong);
	
	public static final boolean BUpdateUserData(CSteamID steamIDUser, String pchPlayerName, int uScore){
		return nBUpdateUserData(steamIDUser.uint64, pchPlayerName, uScore);
	}
	private static final native boolean nBUpdateUserData(long steamIDUser, String pchPlayerName, int uScore);
	
	// callbacks from steam
	private static final ArrayList<ISteamGameServerListener> listeners = new ArrayList<ISteamGameServerListener>();
	
	public static synchronized final void addListener(ISteamGameServerListener listener){
		removeListener(listener);
		listeners.add(listener);
	}
	
	public static synchronized final void removeListener(ISteamGameServerListener listener){
		listeners.remove(listener);
	}
	
	public static synchronized final void OnSteamServersConnected(int pLogonSuccess){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnSteamServersConnected(pLogonSuccess);
		}
	}
	
	public static synchronized final void OnSteamServersDisconnected(int k_iCallback, int m_eResult){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnSteamServersDisconnected(k_iCallback, EResult.FROM_INT_VALUE.get(Integer.valueOf(m_eResult)));
		}
	}
	
	public static synchronized final void OnPolicyResponse(int k_iCallback, boolean m_bSecure){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnPolicyResponse(k_iCallback, m_bSecure);
		}
	}
	
	public static synchronized final void OnGSClientApprove(int k_iCallback, long steamID){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnGSClientApprove(k_iCallback, new CSteamID(steamID));
		}
	}
	
	public static synchronized final void OnGSClientDeny(int k_iCallback, int eDenyReason, String optionalText, long steamID){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnGSClientDeny(k_iCallback, EDenyReason.FROM_INT_VALUE.get(eDenyReason), optionalText, new CSteamID(steamID));
		}
	}
	
	public static synchronized final void OnGSClientKick(int k_iCallback, int eDenyReason, long steamID){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnGSClientKick(k_iCallback, EDenyReason.FROM_INT_VALUE.get(eDenyReason), new CSteamID(steamID));
		}
	}

	public static synchronized final void OnP2PSessionConnectFail(int k_iCallback, int p2PSessionError, long steamID){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnP2PSessionConnectFail(k_iCallback, p2PSessionError, new CSteamID(steamID));
		}
	}
	
	public static synchronized final void OnP2PSessionRequest(int k_iCallback, long steamID){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).OnP2PSessionRequest(k_iCallback, new CSteamID(steamID));
		}
	}
}
