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
