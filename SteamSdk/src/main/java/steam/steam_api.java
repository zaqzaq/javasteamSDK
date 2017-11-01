package steam;

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import steam.exception.SteamException;

public class steam_api {
	private static boolean loadedLibraries = false;
	private static boolean attemptedToLoadLibraries = false;

	private static boolean initCalled = false;
	private static boolean initSuccessFul = false;

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	public static synchronized void loadNativeLibrariesFromJavaLibraryPath() {
		if (loadedLibraries) {
			return;
		}
		attemptedToLoadLibraries = true;
		if (OS_NAME.startsWith("mac")) {
			System.loadLibrary("steam.jni");
			System.loadLibrary("steam_api");
		} else {
			System.loadLibrary("steam_api");
			System.loadLibrary("steam.jni");
		}
		LOG.info("Steam library loaded");
		loadedLibraries = true;
	}

	static public boolean isWindows = System.getProperty("os.name").contains("Windows");
	static public boolean isLinux = System.getProperty("os.name").contains("Linux");
	static public boolean isMac = System.getProperty("os.name").contains("Mac");
	// NOTE: os.arch is based on the architecture of the currently running JVM (not the computer's Operating System)
	static public boolean is64Bit = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64");

	public static void loadLibrary(final File dir, final String libraryName) {
		String filename;
		if (isWindows) {
			filename = libraryName + (is64Bit ? "64.dll" : ".dll");
		} else if (isLinux) {
			filename = "lib" + libraryName + (is64Bit ? "64.so" : ".so");
		} else if (isMac) {
			filename = "lib" + libraryName + ".dylib";
		} else {
			filename = libraryName;
		}
		System.load(new File(dir, filename).getAbsolutePath());
	}

	public static synchronized void loadNativeLibrariesFromDir(final File steamNativeDir) {
		if (loadedLibraries) {
			return;
		}
		attemptedToLoadLibraries = true;

		loadLibrary(steamNativeDir, "steam_api");
		LOG.info("Steam library api loaded");
		loadLibrary(steamNativeDir, "steam.jni");
		LOG.info("Steam library jni loaded");

		loadedLibraries = true;
	}

	public static final void SteamAPI_Init(final long steamAppId) throws SteamException {
		initCalled = true;
		nSteamAPI_Init(steamAppId);
		initSuccessFul = true;
	}

	private static final native void nSteamAPI_Init(long steamAppId) throws SteamException;

	public static final void SteamAPI_Shutdown() throws SteamException {
		nSteamAPI_Shutdown();
	}

	private static final native void nSteamAPI_Shutdown() throws SteamException;

	public static final void SteamAPI_RunCallbacks() {
		nSteamAPI_RunCallbacks();
	}

	private static final native void nSteamAPI_RunCallbacks();

	public static final void logFromNative(final String strToLog) {
		LOG.info("Steam: " + strToLog);
	}

	public static boolean isAttemptedToLoadLibraries() {
		return attemptedToLoadLibraries;
	}

	public static boolean isLoadedLibraries() {
		return loadedLibraries;
	}

	public static boolean isLoadSuccessful() {
		return attemptedToLoadLibraries && loadedLibraries;
	}

	public static boolean isInitCalled() {
		return initCalled;
	}

	public static boolean isInitSuccessFul() {
		return initSuccessFul;
	}

	public static boolean isSteamRunning() {
		return initCalled && initSuccessFul;
	}
}
