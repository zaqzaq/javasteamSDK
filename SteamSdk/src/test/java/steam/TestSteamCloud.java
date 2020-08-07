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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TestSteamCloud extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	protected void setUp() throws Exception {
		LOG.info("setUp: initializing Steam.");
		steam_api.loadNativeLibrariesIntoDir(new File("build/dist"));
		steam_api.SteamAPI_Init(steam_apiTest.STEAM_APP_ID_TEST);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void tearDown() throws Exception {
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
			final String str = new String(bytes, StandardCharsets.UTF_8);
			System.out.println("Contents: `" + str + "'");

			final boolean filePersisted = ISteamRemoteStorage.FilePersisted(fileNameAndSize.getName());
			assertTrue(filePersisted);
		}

		if (avail <= 0) {
			LOG.warn("Available bytes is 0");
			return;
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
}
