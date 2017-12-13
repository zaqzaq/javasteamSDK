package steam;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class TestSteamCloud extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static Thread callbackThread;

	@Override
	protected void setUp() throws Exception {
		LOG.info("setUp: initializing Steam.");
		steam_api.loadNativeLibrariesFromJavaLibraryPath();
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
	public void testSteamCloud() {
		try {
			steamCloud();
		} catch (final Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	public void steamCloud() {
		//
		final int avail = ISteamRemoteStorage.GetQuotaAvailableBytes();
		System.out.println("AvailableBytes = " + avail);

		final int totalBytes = ISteamRemoteStorage.GetQuotaTotalBytes();
		System.out.println("TotalBytes = " + totalBytes);

		final int fileCount = ISteamRemoteStorage.GetFileCount();
		System.out.println("fileCount = " + fileCount);

		for (int i = 0; i < fileCount; i++) {
			final SteamFileNameAndSize fileNameAndSize = ISteamRemoteStorage.GetFileNameAndSize(i);
			System.out.println("file[" + i + "] = " + fileNameAndSize);
			final ByteBuffer buffer = ByteBuffer.allocateDirect(fileNameAndSize.getSize()).order(ByteOrder.nativeOrder());
			ISteamRemoteStorage.FileRead(fileNameAndSize.getName(), buffer);
			buffer.clear();
			final byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			try {
				final String str = new String(bytes, "utf-8");
				System.out.println("Contents: `" + str + "'");
			} catch (final UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			final boolean filePersisted = ISteamRemoteStorage.FilePersisted(fileNameAndSize.getName());
			assertTrue(filePersisted);
		}

		// write and read a file
		final String fileName = "NimblyGames.txt";

		final boolean fileExists = ISteamRemoteStorage.FileExists(fileName);
		System.out.println("File exists: " + fileExists);

		final byte[] writtenFileData = new byte[5];
		final ByteBuffer pvData = ByteBuffer.allocateDirect(writtenFileData.length).order(ByteOrder.nativeOrder());
		for (int i = 0; i < pvData.capacity(); i++) {
			writtenFileData[i] = (byte) ('a' + i);
		}
		pvData.put(writtenFileData);
		pvData.flip();

		System.out.println("Writing file " + fileName);
		final boolean wroteFile = ISteamRemoteStorage.FileWrite(fileName, pvData);
		assertTrue(wroteFile);

		System.out.println("Checking if file persisted " + fileName);
		final boolean filePersisted = ISteamRemoteStorage.FilePersisted(fileName);
		assertTrue(filePersisted);

		final int fileSize = ISteamRemoteStorage.GetFileSize(fileName);
		System.out.println("File " + fileName + " size = " + fileSize);
		final ByteBuffer readBuffer = ByteBuffer.allocateDirect(fileSize).order(ByteOrder.nativeOrder());
		final int numBytesRead = ISteamRemoteStorage.FileRead(fileName, readBuffer);
		readBuffer.position(0);
		readBuffer.limit(numBytesRead);

		final byte[] readData = new byte[readBuffer.remaining()];
		readBuffer.get(readData);
		assertTrue(Arrays.equals(writtenFileData, readData));

		ISteamRemoteStorage.FileDelete(fileName);
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
