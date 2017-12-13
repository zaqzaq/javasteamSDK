package steam;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISteamUserStats {
	private static final String UTF_8_CHARSET = "UTF-8";

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static ByteBuffer tempBuffer = ByteBuffer.allocateDirect(200).order(ByteOrder.nativeOrder());

	private static final ArrayList<ISteamUserStatsListener> listeners = new ArrayList<>();
	private static final ArrayList<ISteamUserStatsListener> listenersTemp = new ArrayList<>();

	public static boolean RequestCurrentStats() {
		return nRequestCurrentStats();
	}

	private static native boolean nRequestCurrentStats();

	public static boolean StoreStats() {
		return nStoreStats();
	}

	private static native boolean nStoreStats();

	public static boolean GetAchievement(final String pchName) {
		tempBuffer.position(0);
		tempBuffer.limit(tempBuffer.capacity());
		try {
			tempBuffer.put(pchName.getBytes(UTF_8_CHARSET));
		} catch (final UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		tempBuffer.put((byte) 0);
		tempBuffer.limit(tempBuffer.position());
		return nGetAchievement(tempBuffer);
	}

	private static native boolean nGetAchievement(ByteBuffer tempBuffer);

	public static boolean SetAchievement(final String pchName) {
		tempBuffer.position(0);
		tempBuffer.limit(tempBuffer.capacity());
		try {
			tempBuffer.put(pchName.getBytes(UTF_8_CHARSET));
		} catch (final UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		tempBuffer.put((byte) 0);
		tempBuffer.limit(tempBuffer.position());
		return nSetAchievement(tempBuffer);
	}

	private static native boolean nSetAchievement(ByteBuffer tempBuffer);

	public static boolean ClearAchievement(final String pchName) {
		tempBuffer.position(0);
		tempBuffer.limit(tempBuffer.capacity());
		try {
			tempBuffer.put(pchName.getBytes(UTF_8_CHARSET));
		} catch (final UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		tempBuffer.put((byte) 0);
		tempBuffer.limit(tempBuffer.position());
		return nClearAchievement(tempBuffer);
	}

	private static native boolean nClearAchievement(ByteBuffer tempBuffer);

	public static void FindLeaderboard(final String pchLeaderboardName) {
		nFindLeaderboard(pchLeaderboardName);
	}

	private static native void nFindLeaderboard(String pchLeaderboardName);

	public static void FindOrCreateLeaderboard(final String pchLeaderboardName, final ELeaderboardSortMethod eLeaderboardSortMethod,
			final ELeaderboardDisplayType eLeaderboardDisplayType) {
		nFindOrCreateLeaderboard(pchLeaderboardName, eLeaderboardSortMethod.toInt(), eLeaderboardDisplayType.toInt());
	}

	private static native void nFindOrCreateLeaderboard(String pchLeaderboardName, int eLeaderboardSortMethod, int eLeaderboardDisplayType);

	public static String GetLeaderboardName(final SteamLeaderboard_t hSteamLeaderboard) {
		return nGetLeaderboardName(hSteamLeaderboard.steamLeaderboard_t);
	}

	private static native String nGetLeaderboardName(long hSteamLeaderboard);

	/**
	 * returns the total number of entries in a leaderboard, as of the last request
	 * 
	 * @param hSteamLeaderboard
	 * @return
	 */
	public static int GetLeaderboardEntryCount(final SteamLeaderboard_t hSteamLeaderboard) {
		return nGetLeaderboardEntryCount(hSteamLeaderboard.steamLeaderboard_t);
	}

	public static native int nGetLeaderboardEntryCount(long hSteamLeaderboard);

	public static ELeaderboardSortMethod GetLeaderboardSortMethod(final SteamLeaderboard_t hSteamLeaderboard) {
		return ELeaderboardSortMethod.fromInt(nGetLeaderboardSortMethod(hSteamLeaderboard.steamLeaderboard_t));
	}

	private static native int nGetLeaderboardSortMethod(long hSteamLeaderboard);

	public static ELeaderboardDisplayType GetLeaderboardDisplayType(final SteamLeaderboard_t hSteamLeaderboard) {
		return ELeaderboardDisplayType.fromInt(nGetLeaderboardDisplayType(hSteamLeaderboard.steamLeaderboard_t));
	}

	private static native int nGetLeaderboardDisplayType(long hSteamLeaderboard);

	public static void DownloadLeaderboardEntries(final SteamLeaderboard_t hSteamLeaderboard, final ELeaderboardDataRequest eLeaderboardDataRequest,
			final int nRangeStart, final int nRangeEnd) {
		nDownloadLeaderboardEntries(hSteamLeaderboard.steamLeaderboard_t, eLeaderboardDataRequest.toInt(), nRangeStart, nRangeEnd);
	}

	private static native void nDownloadLeaderboardEntries(long hSteamLeaderboard, int eLeaderboardDataRequest, int nRangeStart, int nRangeEnd);

	/**
	 * as above, but downloads leaderboard entries for an arbitrary set of users - ELeaderboardDataRequest is k_ELeaderboardDataRequestUsers if a user doesn't
	 * have a leaderboard entry, they won't be included in the result a max of 100 users can be downloaded at a time, with only one outstanding call at a time
	 * 
	 * @param hSteamLeaderboard
	 * @param prgUsers
	 * @param cUsers
	 */
	public static void DownloadLeaderboardEntriesForUsers(final SteamLeaderboard_t hSteamLeaderboard, final CSteamID prgUsers, final int cUsers) {
		nDownloadLeaderboardEntriesForUsers(hSteamLeaderboard.steamLeaderboard_t, prgUsers.uint64, cUsers);
	}

	private static native void nDownloadLeaderboardEntriesForUsers(long hSteamLeaderboard, long prgUsers, int cUsers);

	/**
	 * // Returns data about a single leaderboard entry // use a for loop from 0 to LeaderboardScoresDownloaded_t::m_cEntryCount to get all the downloaded
	 * entries // e.g. // void OnLeaderboardScoresDownloaded( LeaderboardScoresDownloaded_t *pLeaderboardScoresDownloaded ) // { // for ( int index = 0; index <
	 * pLeaderboardScoresDownloaded->m_cEntryCount; index++ ) // { // LeaderboardEntry_t leaderboardEntry; // int32 details[3]; // we know this is how many
	 * we've stored previously // GetDownloadedLeaderboardEntry( pLeaderboardScoresDownloaded->m_hSteamLeaderboardEntries, index, &leaderboardEntry, details, 3
	 * ); // assert( leaderboardEntry.m_cDetails == 3 ); // ... // } // once you've accessed all the entries, the data will be free'd, and the
	 * SteamLeaderboardEntries_t handle will become invalid
	 * 
	 * @param hSteamLeaderboardEntries
	 * @param index
	 * @return
	 */
	public static boolean GetDownloadedLeaderboardEntry(final SteamLeaderboardEntries_t hSteamLeaderboardEntries, final int index,
			final LeaderboardEntry_t pLeaderboardEntry, final IntBuffer pDetails) {
		if (!pDetails.isDirect() && pDetails.order() != ByteOrder.nativeOrder()) {
			throw new IllegalArgumentException("pScoreDetails must be a direct native order buffer");
		}
		return nGetDownloadedLeaderboardEntry(hSteamLeaderboardEntries.steamLeaderboardEntries, index, pLeaderboardEntry, pDetails);
	}

	private static native boolean nGetDownloadedLeaderboardEntry(long hSteamLeaderboardEntries, int index, LeaderboardEntry_t pLeaderboardEntry,
			IntBuffer pDetails);

	public static void UploadLeaderboardScore(final SteamLeaderboard_t hSteamLeaderboard, final ELeaderboardUploadScoreMethod eLeaderboardUploadScoreMethod,
			final int nScore, final IntBuffer pScoreDetails) {
		if (!pScoreDetails.isDirect() && pScoreDetails.order() != ByteOrder.nativeOrder()) {
			throw new IllegalArgumentException("pScoreDetails must be a direct native order buffer");
		}
		nUploadLeaderboardScore(hSteamLeaderboard.steamLeaderboard_t, eLeaderboardUploadScoreMethod.toInt(), nScore, pScoreDetails);
	}

	private static native void nUploadLeaderboardScore(long hSteamLeaderboard, int eLeaderboardUploadScoreMethod, int nScore, IntBuffer pScoreDetails);

	public static void addListener(final ISteamUserStatsListener newListener) {
		removeListener(newListener);
		listeners.add(newListener);
	}

	public static void removeListener(final ISteamUserStatsListener oldListener) {
		listeners.remove(oldListener);
	}

	public static void OnLeaderboardScoresDownloaded(final LeaderboardScoresDownloaded_t pParam, final boolean ioFailure) {
		listenersTemp.clear();
		listenersTemp.addAll(listeners);
		for (int i = 0; i < listenersTemp.size(); i++) {
			listenersTemp.get(i).OnLeaderboardScoresDownloaded(pParam, ioFailure);
		}
	}

	public static void OnFindLeaderboard(final LeaderboardFindResult_t pFindLeaderboardResult, final boolean bIOFailure) {
		listenersTemp.clear();
		listenersTemp.addAll(listeners);
		for (int i = 0; i < listenersTemp.size(); i++) {
			listenersTemp.get(i).OnFindLeaderboard(pFindLeaderboardResult, bIOFailure);
		}
	}

	public static void OnUploadScore(final LeaderboardScoreUploaded_t pScoreUploadedResult, final boolean bIOFailure) {
		listenersTemp.clear();
		listenersTemp.addAll(listeners);
		for (int i = 0; i < listenersTemp.size(); i++) {
			listenersTemp.get(i).OnUploadScore(pScoreUploadedResult, bIOFailure);
		}
	}

	/*
	 * =========================
	 * -------------------------
	 * methods that take a listener
	 * =========================
	 */
	public static void FindLeaderboard(final String pchLeaderboardName, final SteamUserStatsListener listener) {
		if (listener.getCppCallbackInstance() == 0) {
			throw new RuntimeException("SteamUserStatsListener was destroyed");
		}
		nFindLeaderboardListener(pchLeaderboardName, listener.getCppCallbackInstance());
	}

	private static native void nFindLeaderboardListener(String pchLeaderboardName, long listener);

	public static void FindOrCreateLeaderboard(final String pchLeaderboardName, final ELeaderboardSortMethod eLeaderboardSortMethod,
			final ELeaderboardDisplayType eLeaderboardDisplayType, final SteamUserStatsListener listener) {
		if (listener.getCppCallbackInstance() == 0) {
			throw new RuntimeException("SteamUserStatsListener was destroyed");
		}
		nFindOrCreateLeaderboardListener(pchLeaderboardName, eLeaderboardSortMethod.toInt(), eLeaderboardDisplayType.toInt(),
				listener.getCppCallbackInstance());
	}

	private static native void nFindOrCreateLeaderboardListener(String pchLeaderboardName, int eLeaderboardSortMethod, int eLeaderboardDisplayType,
			long listener);

	public static void DownloadLeaderboardEntries(final SteamLeaderboard_t hSteamLeaderboard, final ELeaderboardDataRequest eLeaderboardDataRequest,
			final int nRangeStart, final int nRangeEnd, final SteamUserStatsListener listener) {
		if (listener.getCppCallbackInstance() == 0) {
			throw new RuntimeException("SteamUserStatsListener was destroyed");
		}
		nDownloadLeaderboardEntriesListener(hSteamLeaderboard.steamLeaderboard_t, eLeaderboardDataRequest.toInt(), nRangeStart, nRangeEnd,
				listener.getCppCallbackInstance());
	}

	private static native void nDownloadLeaderboardEntriesListener(long hSteamLeaderboard, int eLeaderboardDataRequest, int nRangeStart, int nRangeEnd,
			long listener);

	/**
	 * as above, but downloads leaderboard entries for an arbitrary set of users - ELeaderboardDataRequest is k_ELeaderboardDataRequestUsers if a user doesn't
	 * have a leaderboard entry, they won't be included in the result a max of 100 users can be downloaded at a time, with only one outstanding call at a time
	 * 
	 * @param hSteamLeaderboard
	 * @param prgUsers
	 * @param cUsers
	 */
	public static void DownloadLeaderboardEntriesForUsers(final SteamLeaderboard_t hSteamLeaderboard, final CSteamID prgUsers, final int cUsers,
			final SteamUserStatsListener listener) {
		if (listener.getCppCallbackInstance() == 0) {
			throw new RuntimeException("SteamUserStatsListener was destroyed");
		}
		nDownloadLeaderboardEntriesForUsersListener(hSteamLeaderboard.steamLeaderboard_t, prgUsers.uint64, cUsers, listener.getCppCallbackInstance());
	}

	private static native void nDownloadLeaderboardEntriesForUsersListener(long hSteamLeaderboard, long prgUsers, int cUsers, long listener);

	public static void UploadLeaderboardScore(final SteamLeaderboard_t hSteamLeaderboard, final ELeaderboardUploadScoreMethod eLeaderboardUploadScoreMethod,
			final int nScore, final IntBuffer pScoreDetails, final SteamUserStatsListener listener) {
		if (!pScoreDetails.isDirect() && pScoreDetails.order() != ByteOrder.nativeOrder()) {
			throw new IllegalArgumentException("pScoreDetails must be a direct native order buffer");
		}
		if (listener.getCppCallbackInstance() == 0) {
			throw new RuntimeException("SteamUserStatsListener was destroyed");
		}
		nUploadLeaderboardScoreListener(hSteamLeaderboard.steamLeaderboard_t, eLeaderboardUploadScoreMethod.toInt(), nScore, pScoreDetails,
				listener.getCppCallbackInstance());
	}

	private static native void nUploadLeaderboardScoreListener(long hSteamLeaderboard, int eLeaderboardUploadScoreMethod, int nScore, IntBuffer pScoreDetails,
			long listener);
}
