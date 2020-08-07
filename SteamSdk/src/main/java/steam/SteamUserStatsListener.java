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

public abstract class SteamUserStatsListener {
	private long cppCallbackInstance;

	/**
	 * Call destroy when you're done with this object otherwise it will never get garbage collected
	 */
	public SteamUserStatsListener() {
		super();

		cppCallbackInstance = nInitializeCppCallback();
	}

	public void destroy() {
		if (cppCallbackInstance != 0) {
			nFreeCppCallback(cppCallbackInstance);
		}
		cppCallbackInstance = 0;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		destroy();
	}

	public long getCppCallbackInstance() {
		return cppCallbackInstance;
	}

	private native long nInitializeCppCallback();

	private native void nFreeCppCallback(long cppCallbackInstance);

	public abstract void onLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t pParam, boolean ioFailure);

	public abstract void onFindLeaderboard(LeaderboardFindResult_t pFindLeaderboardResult, boolean bIOFailure);

	public abstract void onUploadScore(LeaderboardScoreUploaded_t pScoreUploadedResult, boolean bIOFailure);
}
