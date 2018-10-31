/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

import java.util.HashMap;

public enum EAccountType {
	k_EAccountTypeInvalid,			
	k_EAccountTypeIndividual,			// single user account
	k_EAccountTypeMultiseat,			// multiseat (e.g. cybercafe) account
	k_EAccountTypeGameServer,			// game server account
	k_EAccountTypeAnonGameServer,	// anonymous game server account
	k_EAccountTypePending,				// pending
	k_EAccountTypeContentServer,		// content server
	k_EAccountTypeClan,
	k_EAccountTypeChat,
	k_EAccountTypeP2PSuperSeeder,	// a fake steamid used by superpeers to seed content to users of Steam P2P stuff
	k_EAccountTypeAnonUser;
	
	private static final HashMap<Integer, EAccountType> FROM_ORDINAL = new HashMap<Integer, EAccountType>();
	public static final EAccountType[] VALUES;
	static{
		VALUES = values();
		for(int i = 0; i < VALUES.length; i++){
			FROM_ORDINAL.put(Integer.valueOf(VALUES[i].ordinal()), VALUES[i]);
		}
	}
	
	/**
	 * @param ordinal
	 * @return k_EAccountTypeInvalid if ordinal is invalid
	 */
	public static final EAccountType fromOrdinal(int ordinal){
		EAccountType accountType = FROM_ORDINAL.get(Integer.valueOf(ordinal));
		if(accountType==null){
			accountType = k_EAccountTypeInvalid;
		}
		return accountType;
	}
}
