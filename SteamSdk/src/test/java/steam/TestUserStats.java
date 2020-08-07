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

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Random;

public class TestUserStats extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static Thread callbackThread;

	@Override
	protected void setUp() throws Exception {
		LOG.info("setUp: initializing Steam.");
		steam_api.loadNativeLibrariesIntoDir(new File("build/dist"));
		steam_api.SteamAPI_Init(steam_apiTest.STEAM_APP_ID_TEST);
		callbackThread = startRunCallbackThread();
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void tearDown() throws Exception {
		callbackThread.stop();
		steam_api.SteamAPI_Shutdown();
	}

	@Test
	public void testStatsCatch() throws Throwable {
		try {
			stats();
		} catch (final Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	@SuppressWarnings("unused")
	private boolean ioFailure = false;
	private LeaderboardScoreUploaded_t scoreUpload;
	private LeaderboardScoresDownloaded_t scoreDownload;
	private LeaderboardFindResult_t foundLeaderboard;

	public void stats() throws Exception {
		final Object onUploadScoreMutex = new Object();
		final Object onLeaderboardScoresDownloadedMutex = new Object();
		final Object onFindLeaderboardMutex = new Object();

		final ISteamUserStatsListener statsListener = new ISteamUserStatsListener() {
			@Override
			public void OnUploadScore(final LeaderboardScoreUploaded_t pScoreUploadedResult, final boolean bIOFailure) {
				ioFailure = bIOFailure;
				scoreUpload = pScoreUploadedResult;
				synchronized (onUploadScoreMutex) {
					onUploadScoreMutex.notifyAll();
				}
			}

			@Override
			public void OnLeaderboardScoresDownloaded(final LeaderboardScoresDownloaded_t pParam, final boolean ioFailure) {
				TestUserStats.this.ioFailure = ioFailure;
				scoreDownload = pParam;
				synchronized (onLeaderboardScoresDownloadedMutex) {
					onLeaderboardScoresDownloadedMutex.notifyAll();
				}
			}

			@Override
			public void OnFindLeaderboard(final LeaderboardFindResult_t pFindLeaderboardResult, final boolean bIOFailure) {
				TestUserStats.this.ioFailure = bIOFailure;
				foundLeaderboard = pFindLeaderboardResult;
				synchronized (onFindLeaderboardMutex) {
					onFindLeaderboardMutex.notifyAll();
				}
			}
		};
		ISteamUserStats.addListener(statsListener);

		/*
		 * ************* Find a leaderboard by name **********************
		 */
		ISteamUserStats.FindLeaderboard("Quickest Win");
		synchronized (onFindLeaderboardMutex) {
			onFindLeaderboardMutex.wait(3000);
		}
		if (foundLeaderboard == null) {
			throw new Exception("Couldnt find leader board...");
		}
		System.out.println("Found leaderboard getM_bLeaderboardFound = " + foundLeaderboard.getM_bLeaderboardFound());
		System.out.println("Found leaderboard getM_hSteamLeaderboard = " + foundLeaderboard.getM_hSteamLeaderboard());

		/*
		 * find leaderboard using instanced listener api
		 */
		final SteamUserStatsListener listener = new SteamUserStatsListener() {
			@Override
			public void onUploadScore(final LeaderboardScoreUploaded_t pScoreUploadedResult, final boolean bIOFailure) {
				ioFailure = bIOFailure;
				scoreUpload = pScoreUploadedResult;
				synchronized (onUploadScoreMutex) {
					onUploadScoreMutex.notifyAll();
				}
			}

			@Override
			public void onLeaderboardScoresDownloaded(final LeaderboardScoresDownloaded_t pParam, final boolean ioFailure) {
				TestUserStats.this.ioFailure = ioFailure;
				scoreDownload = pParam;
				synchronized (onLeaderboardScoresDownloadedMutex) {
					onLeaderboardScoresDownloadedMutex.notifyAll();
				}
			}

			@Override
			public void onFindLeaderboard(final LeaderboardFindResult_t pFindLeaderboardResult, final boolean bIOFailure) {
				TestUserStats.this.ioFailure = bIOFailure;
				foundLeaderboard = pFindLeaderboardResult;
				synchronized (onFindLeaderboardMutex) {
					onFindLeaderboardMutex.notifyAll();
				}
			}
		};
		ISteamUserStats.FindLeaderboard("Quickest Win", listener);
		synchronized (onFindLeaderboardMutex) {
			onFindLeaderboardMutex.wait(3000);
		}
		if (foundLeaderboard == null) {
			throw new Exception("Couldnt find leader board...");
		}
		System.out.println("============= Found Leaderboard listener api ========================");
		System.out.println("Found leaderboard getM_bLeaderboardFound = " + foundLeaderboard.getM_bLeaderboardFound());
		System.out.println("Found leaderboard getM_hSteamLeaderboard = " + foundLeaderboard.getM_hSteamLeaderboard());

		/*
		 * ************* Download top 10 entries for a leaderboard **********************
		 */
		ISteamUserStats.DownloadLeaderboardEntries(foundLeaderboard.getM_hSteamLeaderboard(), ELeaderboardDataRequest.k_ELeaderboardDataRequestGlobal, 0, 10);
		synchronized (onLeaderboardScoresDownloadedMutex) {
			onLeaderboardScoresDownloadedMutex.wait(3000);
		}
		if (scoreDownload == null) {
			throw new Exception("Couldn't download leaderboard entries...");
		}
		System.out.println("Scores downloaded scoreDownload.getM_cEntryCount() = " + scoreDownload.getM_cEntryCount());
		System.out.println("Scores downloaded getM_hSteamLeaderboard() = " + scoreDownload.getM_hSteamLeaderboard());
		System.out.println("Scores downloaded getM_hSteamLeaderboardEntries() = " + scoreDownload.getM_hSteamLeaderboardEntries());

		System.out.println("Leaderboard entries:");
		for (int i = 0; i < scoreDownload.getM_cEntryCount(); i++) {
			final LeaderboardEntry_t entry = new LeaderboardEntry_t();
			final IntBuffer pDetails = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			final boolean gotEntry = ISteamUserStats.GetDownloadedLeaderboardEntry(scoreDownload.getM_hSteamLeaderboardEntries(), i, entry, pDetails);
			System.out.println("gotEntry = " + gotEntry);
			System.out.println("entry.getM_cDetails() = " + entry.getM_cDetails());
			System.out.println("entry.getM_hUGC() = " + entry.getM_hUGC());
			System.out.println("entry.getM_nGlobalRank() = " + entry.getM_nGlobalRank());
			System.out.println("entry.getM_nScore() = " + entry.getM_nScore());
			System.out.println("entry.getM_steamIDUser() = " + entry.getM_steamIDUser());
			System.out.println("Persona name = " + ISteamFriends.GetFriendPersonaName(entry.getM_steamIDUser()));
			System.out.println();
		}

		/*
		 * ************* Submit a new score **********************
		 */
		final Random rand = new Random();
		IntBuffer scoreData = ByteBuffer.allocateDirect(300 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		while (scoreData.remaining() > 0) {
			scoreData.put(rand.nextInt());
		}
		scoreData.clear();
		ISteamUserStats.UploadLeaderboardScore(foundLeaderboard.getM_hSteamLeaderboard(),
				ELeaderboardUploadScoreMethod.k_ELeaderboardUploadScoreMethodForceUpdate, Integer.MAX_VALUE, scoreData);
		synchronized (onUploadScoreMutex) {
			onUploadScoreMutex.wait(3000);
		}
		if (scoreUpload == null) {
			throw new Exception("Failed to upload score...");
		}
		System.out.println("scoreUpload.getM_bScoreChanged() = " + scoreUpload.getM_bScoreChanged());
		System.out.println("scoreUpload.getM_bSuccess() = " + scoreUpload.getM_bSuccess());
		System.out.println("scoreUpload.getM_hSteamLeaderboard() = " + scoreUpload.getM_hSteamLeaderboard());
		System.out.println("scoreUpload.getM_nGlobalRankNew() = " + scoreUpload.getM_nGlobalRankNew());
		System.out.println("scoreUpload.getM_nGlobalRankPrevious() = " + scoreUpload.getM_nGlobalRankPrevious());
		System.out.println("scoreUpload.getM_nScore()= " + scoreUpload.getM_nScore());

		/*
		 * ************* get score and data for this user *******************
		 */
		scoreDownload = null;
		ISteamUserStats.DownloadLeaderboardEntriesForUsers(foundLeaderboard.getM_hSteamLeaderboard(), ISteamUser.GetSteamID(), 1);
		synchronized (onLeaderboardScoresDownloadedMutex) {
			onLeaderboardScoresDownloadedMutex.wait(3000);
		}
		if (scoreDownload == null) {
			throw new Exception("Couldn't get score for this user");
		}
		System.out.println("Scores downloaded scoreDownload.getM_cEntryCount() = " + scoreDownload.getM_cEntryCount());
		System.out.println("Scores downloaded getM_hSteamLeaderboard() = " + scoreDownload.getM_hSteamLeaderboard());
		System.out.println("Scores downloaded getM_hSteamLeaderboardEntries() = " + scoreDownload.getM_hSteamLeaderboardEntries());

		System.out.println("Leaderboard entries:");
		for (int i = 0; i < scoreDownload.getM_cEntryCount(); i++) {
			final LeaderboardEntry_t entry = new LeaderboardEntry_t();
			final IntBuffer pDetails = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			final boolean gotEntry = ISteamUserStats.GetDownloadedLeaderboardEntry(scoreDownload.getM_hSteamLeaderboardEntries(), i, entry, pDetails);
			System.out.println("gotEntry = " + gotEntry);
			System.out.println("entry.getM_cDetails() = " + entry.getM_cDetails());
			System.out.println("entry.getM_hUGC() = " + entry.getM_hUGC());
			System.out.println("entry.getM_nGlobalRank() = " + entry.getM_nGlobalRank());
			System.out.println("entry.getM_nScore() = " + entry.getM_nScore());
			System.out.println("entry.getM_steamIDUser() = " + entry.getM_steamIDUser());
			System.out.println("Persona name = " + ISteamFriends.GetFriendPersonaName(entry.getM_steamIDUser()));
			System.out.println();
		}

		/*
		 * ************* create a new nimbly games leaderboard *******************
		 */
		System.out.println("================= =================");
		System.out.println("================= Custom leaderboard =================");
		System.out.println("================= =================");
		System.out.println("================= =================");
		foundLeaderboard = null;
		ISteamUserStats.FindOrCreateLeaderboard("NimblyGamesLLC", ELeaderboardSortMethod.k_ELeaderboardSortMethodDescending,
				ELeaderboardDisplayType.k_ELeaderboardDisplayTypeNumeric, listener);
		synchronized (onFindLeaderboardMutex) {
			onFindLeaderboardMutex.wait(3000);
		}
		if (foundLeaderboard == null) {
			throw new Exception("Couldnt find custom leaderboard NimblyGamesLLC");
		}
		System.out.println("Found leaderboard getM_bLeaderboardFound = " + foundLeaderboard.getM_bLeaderboardFound());
		System.out.println("Found leaderboard getM_hSteamLeaderboard = " + foundLeaderboard.getM_hSteamLeaderboard());
		System.out.println("Leaderboard name = " + ISteamUserStats.GetLeaderboardName(foundLeaderboard.getM_hSteamLeaderboard()));

		/*
		 * ************* Download top 10 entries for custom nimbly games leaderboard **********************
		 */
		scoreDownload = null;
		ISteamUserStats.DownloadLeaderboardEntries(foundLeaderboard.getM_hSteamLeaderboard(), ELeaderboardDataRequest.k_ELeaderboardDataRequestGlobal, 0, 10,
				listener);
		synchronized (onLeaderboardScoresDownloadedMutex) {
			onLeaderboardScoresDownloadedMutex.wait(3000);
		}
		if (scoreDownload == null) {
			throw new Exception("Couldn't download leaderboard entries...");
		}
		System.out.println("Scores downloaded scoreDownload.getM_cEntryCount() = " + scoreDownload.getM_cEntryCount());
		System.out.println("Scores downloaded getM_hSteamLeaderboard() = " + scoreDownload.getM_hSteamLeaderboard());
		System.out.println("Scores downloaded getM_hSteamLeaderboardEntries() = " + scoreDownload.getM_hSteamLeaderboardEntries());

		System.out.println("Leaderboard entries:");
		for (int i = 0; i < scoreDownload.getM_cEntryCount(); i++) {
			final LeaderboardEntry_t entry = new LeaderboardEntry_t();
			final IntBuffer pDetails = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			final boolean gotEntry = ISteamUserStats.GetDownloadedLeaderboardEntry(scoreDownload.getM_hSteamLeaderboardEntries(), i, entry, pDetails);
			System.out.println("gotEntry = " + gotEntry);
			System.out.println("entry.getM_cDetails() = " + entry.getM_cDetails());
			System.out.println("entry.getM_hUGC() = " + entry.getM_hUGC());
			System.out.println("entry.getM_nGlobalRank() = " + entry.getM_nGlobalRank());
			System.out.println("entry.getM_nScore() = " + entry.getM_nScore());
			System.out.println("entry.getM_steamIDUser() = " + entry.getM_steamIDUser());
			System.out.println("Persona name = " + ISteamFriends.GetFriendPersonaName(entry.getM_steamIDUser()));
			System.out.println();
		}

		/*
		 * ************* Submit a new score for custom leaderboard **********************
		 */
		scoreData = ByteBuffer.allocateDirect(300 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		while (scoreData.remaining() > 0) {
			scoreData.put(rand.nextInt());
		}
		scoreData.clear();
		ISteamUserStats.UploadLeaderboardScore(foundLeaderboard.getM_hSteamLeaderboard(),
				ELeaderboardUploadScoreMethod.k_ELeaderboardUploadScoreMethodForceUpdate, 1, scoreData, listener);
		synchronized (onUploadScoreMutex) {
			onUploadScoreMutex.wait(3000);
		}
		if (scoreUpload == null) {
			throw new Exception("Failed to upload score...");
		}
		System.out.println("scoreUpload.getM_bScoreChanged() = " + scoreUpload.getM_bScoreChanged());
		System.out.println("scoreUpload.getM_bSuccess() = " + scoreUpload.getM_bSuccess());
		System.out.println("scoreUpload.getM_hSteamLeaderboard() = " + scoreUpload.getM_hSteamLeaderboard());
		System.out.println("scoreUpload.getM_nGlobalRankNew() = " + scoreUpload.getM_nGlobalRankNew());
		System.out.println("scoreUpload.getM_nGlobalRankPrevious() = " + scoreUpload.getM_nGlobalRankPrevious());
		System.out.println("scoreUpload.getM_nScore()= " + scoreUpload.getM_nScore());

		/*
		 * ************* get score and data for this user on custom nimblygames leaderboard *******************
		 */
		scoreDownload = null;
		ISteamUserStats.DownloadLeaderboardEntriesForUsers(foundLeaderboard.getM_hSteamLeaderboard(), ISteamUser.GetSteamID(), 1, listener);
		synchronized (onLeaderboardScoresDownloadedMutex) {
			onLeaderboardScoresDownloadedMutex.wait(3000);
		}
		if (scoreDownload == null) {
			throw new Exception("Couldn't get score for this user");
		}
		System.out.println("Scores downloaded scoreDownload.getM_cEntryCount() = " + scoreDownload.getM_cEntryCount());
		System.out.println("Scores downloaded getM_hSteamLeaderboard() = " + scoreDownload.getM_hSteamLeaderboard());
		System.out.println("Scores downloaded getM_hSteamLeaderboardEntries() = " + scoreDownload.getM_hSteamLeaderboardEntries());

		System.out.println("Leaderboard entries:");
		for (int i = 0; i < scoreDownload.getM_cEntryCount(); i++) {
			final LeaderboardEntry_t entry = new LeaderboardEntry_t();
			final IntBuffer pDetails = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			final boolean gotEntry = ISteamUserStats.GetDownloadedLeaderboardEntry(scoreDownload.getM_hSteamLeaderboardEntries(), i, entry, pDetails);
			System.out.println("gotEntry = " + gotEntry);
			System.out.println("entry.getM_cDetails() = " + entry.getM_cDetails());
			System.out.println("entry.getM_hUGC() = " + entry.getM_hUGC());
			System.out.println("entry.getM_nGlobalRank() = " + entry.getM_nGlobalRank());
			System.out.println("entry.getM_nScore() = " + entry.getM_nScore());
			System.out.println("entry.getM_steamIDUser() = " + entry.getM_steamIDUser());
			System.out.println("Persona name = " + ISteamFriends.GetFriendPersonaName(entry.getM_steamIDUser()));
			System.out.println();
		}
	}

	private static Thread startRunCallbackThread() {
		final Thread thread = new Thread(() -> {
			while (true) {
				steam_api.SteamAPI_RunCallbacks();
				try {
					Thread.sleep(50);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		return thread;
	}

}
