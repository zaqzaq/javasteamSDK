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

public interface ISteamGameServerListener {

	void OnSteamServersConnected(int pLogonSuccess);

	void OnSteamServersDisconnected(int kICallback, EResult eResult);

	void OnPolicyResponse(int kICallback, boolean mBSecure);

	void OnGSClientApprove(int kICallback, CSteamID cSteamID);

	void OnGSClientDeny(int kICallback, EDenyReason eDenyReason, String optionalText, CSteamID cSteamID);

	void OnGSClientKick(int kICallback, EDenyReason eDenyReason, CSteamID cSteamID);

	void OnP2PSessionConnectFail(int kICallback, int p2pSessionError, CSteamID cSteamID);

	void OnP2PSessionRequest(int kICallback, CSteamID cSteamID);

}
