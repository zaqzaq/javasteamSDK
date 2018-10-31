/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class EFriendFlags {

	public static final EFriendFlags k_EFriendFlagNone = new EFriendFlags(0x00);
	public static final EFriendFlags k_EFriendFlagBlocked = new EFriendFlags(0x01);
	public static final EFriendFlags k_EFriendFlagFriendshipRequested = new EFriendFlags(0x02);
	/**
	 * "real" friend flag
	 */
	public static final EFriendFlags k_EFriendFlagImmediate = new EFriendFlags(0x04);
	public static final EFriendFlags k_EFriendFlagClanMember = new EFriendFlags(0x08);
	public static final EFriendFlags k_EFriendFlagOnGameServer = new EFriendFlags(0x10);
	public static final EFriendFlags k_EFriendFlagRequestingFriendship = new EFriendFlags(0x80);
	public static final EFriendFlags k_EFriendFlagRequestingInfo = new EFriendFlags(0x100);
	public static final EFriendFlags k_EFriendFlagIgnored = new EFriendFlags(0x200);
	public static final EFriendFlags k_EFriendFlagIgnoredFriend = new EFriendFlags(0x400);
	public static final EFriendFlags k_EFriendFlagAll = new EFriendFlags(0xFFFF);

	int value;

	private EFriendFlags(int value) {
		super();
		this.value = value;
	}
}
