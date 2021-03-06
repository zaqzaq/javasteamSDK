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

import java.util.HashMap;

public enum EResult {
		k_EResultOK(1),								// success
		k_EResultFail(2),								// generic failure 
		k_EResultNoConnection(3),					// no/failed network connection
//		k_EResultNoConnectionRetry(4),			// OBSOLETE - removed
		k_EResultInvalidPassword(5),				// password/ticket is invalid
		k_EResultLoggedInElsewhere(6),			// same user logged in elsewhere
		k_EResultInvalidProtocolVer(7),			// protocol version is incorrect
		k_EResultInvalidParam(8),					// a parameter is incorrect
		k_EResultFileNotFound(9),					// file was not found
		k_EResultBusy(10),							// called method busy - action not taken
		k_EResultInvalidState(11),					// called object was in an invalid state
		k_EResultInvalidName(12),					// name is invalid
		k_EResultInvalidEmail(13),					// email is invalid
		k_EResultDuplicateName(14),				// name is not unique
		k_EResultAccessDenied(15),					// access is denied
		k_EResultTimeout(16),						// operation timed out
		k_EResultBanned(17),							// VAC2 banned
		k_EResultAccountNotFound(18),				// account not found
		k_EResultInvalidSteamID(19),				// steamID is invalid
		k_EResultServiceUnavailable(20),			// The requested service is currently unavailable
		k_EResultNotLoggedOn(21),					// The user is not logged on
		k_EResultPending(22),						// Request is pending (may be in process), or waiting on third party)
		k_EResultEncryptionFailure(23),			// Encryption or Decryption failed
		k_EResultInsufficientPrivilege(24),		// Insufficient privilege
		k_EResultLimitExceeded(25),				// Too much of a good thing
		k_EResultRevoked(26),						// Access has been revoked (used for revoked guest passes)
		k_EResultExpired(27),						// License/Guest pass the user is trying to access is expired
		k_EResultAlreadyRedeemed(28),				// Guest pass has already been redeemed by account, cannot be acked again
		k_EResultDuplicateRequest(29),				// The request is a duplicate and the action has already occurred in the past, ignored this time
		k_EResultAlreadyOwned(30),					// All the games in this guest pass redemption request are already owned by the user
		k_EResultIPNotFound(31),					// IP address not found
		k_EResultPersistFailed(32),				// failed to write change to the data store
		k_EResultLockingFailed(33),				// failed to acquire access lock for this operation
		k_EResultLogonSessionReplaced(34),//
		k_EResultConnectFailed(35),//
		k_EResultHandshakeFailed(36),//
		k_EResultIOFailure(37),//
		k_EResultRemoteDisconnect(38),//
		k_EResultShoppingCartNotFound(39),		// failed to find the shopping cart requested
		k_EResultBlocked(40),						// a user didn't allow it
		k_EResultIgnored(41),						// target is ignoring sender
		k_EResultNoMatch(42),						// nothing matching the request found
		k_EResultAccountDisabled(43),//
		k_EResultServiceReadOnly(44),				// this service is not accepting content changes right now
		k_EResultAccountNotFeatured(45),			// account doesn't have value), so this feature isn't available
		k_EResultAdministratorOK(46),				// allowed to take this action), but only because requester is admin
		k_EResultContentVersion(47),				// A Version mismatch in content transmitted within the Steam protocol.
		k_EResultTryAnotherCM(48),					// The current CM can't service the user making a request), user should try another.
		k_EResultPasswordRequiredToKickSession(49),// You are already logged in elsewhere), this cached credential login has failed.
		k_EResultAlreadyLoggedInElsewhere(50),		// You are already logged in elsewhere, you must wait
		k_EResultSuspended(51),					// Long running operation (content download) suspended/paused
		k_EResultCancelled(52),					// Operation canceled (typically by user: content download)
		k_EResultDataCorruption(53),			// Operation canceled because data is ill formed or unrecoverable
		k_EResultDiskFull(54),					// Operation canceled - not enough disk space.
		k_EResultRemoteCallFailed(55);		// an remote call or IPC call failed
		public static final HashMap<Integer, EResult> FROM_INT_VALUE = new HashMap<Integer, EResult>();
		static{
			EResult[] values = EResult.values();
			for(int i = 0; i < values.length; i++){
				FROM_INT_VALUE.put(Integer.valueOf(values[i].intValue), values[i]);
			}
		}
		public final int intValue;

   EResult(int intValue) {
			this.intValue = intValue;
		}
}
