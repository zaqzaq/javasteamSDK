/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class ISteamFriends {

	/**
	 * returns the local players name - guaranteed to not be NULL. this is the
	 * same name as on the users community profile page this is stored in UTF-8
	 * format like all the other interface functions that return a char *, it's
	 * important that this pointer is not saved off; it will eventually be free'd
	 * or re-allocated
	 * 
	 * @return
	 */
	public static String GetPersonaName() {
		return nGetPersonaName();
	}

	private static native String nGetPersonaName();

	/**
	 * friend iteration takes a set of k_EFriendFlags, and returns the number of
	 * users the client knows about who meet that criteria then
	 * GetFriendByIndex() can then be used to return the id's of each of those
	 * users
	 */
	public static int GetFriendCount(EFriendFlags... iFriendFlags) {
		int flag = 0;
		for (int i = 0; i < iFriendFlags.length; i++) {
			flag |= iFriendFlags[i].value;
		}
		return nGetFriendCount(flag);
	}

	private static native int nGetFriendCount(int flag);

	/**
	 * returns the steamID of a user iFriend is a index of range [0,
	 * GetFriendCount()) iFriendsFlags must be the same value as used in
	 * GetFriendCount() the returned CSteamID can then be used by all the
	 * functions below to access details about the user
	 * 
	 */
	public static CSteamID GetFriendByIndex(int iFriend, EFriendFlags... iFriendFlags) {
		int flag = 0;
		for (int i = 0; i < iFriendFlags.length; i++) {
			flag |= iFriendFlags[i].value;
		}
		long nGetFriendByIndex = nGetFriendByIndex(iFriend, flag);
		return new CSteamID(nGetFriendByIndex);
	}

	private static native long nGetFriendByIndex(int iFriend, int flag);

	public static String GetFriendPersonaName(CSteamID steamIDFriend ){
		return nGetFriendPersonaName(steamIDFriend.uint64);
	}
	private static native String nGetFriendPersonaName(long uint64);
	
	public static int GetClanCount(){
		return nGetClanCount();
	}
	
	private static native int nGetClanCount();
	
	public static CSteamID GetClanByIndex( int iClan ){
		long nGetClanByIndex = nGetClanByIndex(iClan);
		return new CSteamID(nGetClanByIndex);
	}
	
	private static native long nGetClanByIndex(int iClan);
	
	public static String GetClanName( CSteamID steamIDClan ) {
		return nGetClanName(steamIDClan.uint64);
	}

	private static native String nGetClanName(long uint64);
	
	public static int GetSmallFriendAvatar(CSteamID steamIDFriend){
		return nGetSmallFriendAvatar(steamIDFriend.uint64);
	}
	private static native int nGetSmallFriendAvatar(long steamIDFriend);
}
