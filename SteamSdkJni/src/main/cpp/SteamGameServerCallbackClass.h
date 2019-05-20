/*
 * SteamGameServerCallbackClass.h
 *
 *      Author: karl
 */

#ifndef STEAMGAMESERVERCALLBACKCLASS_H_
#define STEAMGAMESERVERCALLBACKCLASS_H_

#include <jni.h>
#include <steam/steam_api.h>
#include <steam/steam_gameserver.h>

namespace steamjni {

class SteamGameServerCallbackClass {
	private:
		JavaVM *jvm;
		void log(const char* theString);
	public:
		explicit SteamGameServerCallbackClass(JavaVM *jvm);
		virtual ~SteamGameServerCallbackClass();
		//
		// Various callback functions that Steam will call to let us know about events related to our
		// connection to the Steam servers for authentication purposes.
		//
		// Tells us when we have successfully connected to Steam
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnSteamServersConnected, SteamServersConnected_t, m_CallbackSteamServersConnected );
		// Tells us when we have been logged out of Steam
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnSteamServersDisconnected, SteamServersDisconnected_t, m_CallbackSteamServersDisconnected );
		// Tells us that Steam has set our security policy (VAC on or off)
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnPolicyResponse, GSPolicyResponse_t, m_CallbackPolicyResponse );
		//
		// Various callback functions that Steam will call to let us know about whether we should
		// allow clients to play or we should kick/deny them.
		//
		// Tells us a client has been authenticated and approved to play by Steam (passes auth, license check, VAC status, etc...)
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnGSClientApprove, GSClientApprove_t, m_CallbackGSClientApprove );
		// Tells us we should deny a player from accessing the server
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnGSClientDeny, GSClientDeny_t, m_CallbackGSClientDeny );
		// Tells us we should kick a player who was previously allowed to play (maybe they are no longer connected
		// to steam so their VAC status cannot be verified)
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnGSClientKick, GSClientKick_t, m_CallbackGSClientKick );
		// client connection state
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnP2PSessionRequest, P2PSessionRequest_t, m_CallbackP2PSessionRequest );
		STEAM_GAMESERVER_CALLBACK( steamjni::SteamGameServerCallbackClass, OnP2PSessionConnectFail, P2PSessionConnectFail_t, m_CallbackP2PSessionConnectFail );
};

}

#endif /* STEAMGAMESERVERCALLBACKCLASS_H_ */
