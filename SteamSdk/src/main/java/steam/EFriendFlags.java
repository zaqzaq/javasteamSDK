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
