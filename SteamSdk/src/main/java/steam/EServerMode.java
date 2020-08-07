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

public enum EServerMode {
	eServerModeInvalid(0), // DO NOT USE		
	eServerModeNoAuthentication(1), // Don't authenticate user logins and don't list on the server list
	eServerModeAuthentication(2), // Authenticate users, list on the server list, don't run VAC on clients that connect
	eServerModeAuthenticationAndSecure(3);
	
	public final int toInt;

   EServerMode(int mode) {
		this.toInt = mode;
	}
}
