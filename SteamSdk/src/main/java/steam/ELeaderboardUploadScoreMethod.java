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

public enum ELeaderboardUploadScoreMethod {
	k_ELeaderboardUploadScoreMethodNone,
	/**
	 * Leaderboard will keep user's best score
	 */
	k_ELeaderboardUploadScoreMethodKeepBest,
	/**
	 *  Leaderboard will always replace score with specified
	 */
	k_ELeaderboardUploadScoreMethodForceUpdate;
	
	public int toInt(){
		return ordinal();
	}
	
	public static ELeaderboardUploadScoreMethod fromInt(int theInt){
		return values()[theInt];
	}
}
