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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steam.exception.SteamException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class steam_api {
   private static boolean loadedLibraries = false;
   private static boolean attemptedToLoadLibraries = false;

   private static boolean initCalled = false;
   private static boolean initSuccessFul = false;

   private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

   private static final boolean isWindows = System.getProperty("os.name").contains("Windows");
   private static final boolean isLinux = System.getProperty("os.name").contains("Linux");
   private static final boolean isMac = System.getProperty("os.name").contains("Mac");
   // NOTE: os.arch is based on the architecture of the currently running JVM (not the computer's Operating System)
   private static final boolean is64Bit = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64");

   private static void extractAndLoadLibrary(final Path dir, final String libraryName) throws IOException {

      final Path sharedLibraryRelativePath;
      if (isWindows) {
         sharedLibraryRelativePath = Paths.get("windows-x86" + (is64Bit ? "-64" : "")).resolve(libraryName + ".dll");
      } else if (isLinux) {
         sharedLibraryRelativePath = Paths.get("linux-x86" + (is64Bit ? "-64" : "")).resolve("lib" + libraryName + ".so");
      } else if (isMac) {
         sharedLibraryRelativePath = Paths.get("macos").resolve("lib" + libraryName + ".dylib");
      } else {
         throw new IllegalStateException("Unsupported operating system");
      }

      String relativeUriString = sharedLibraryRelativePath.toString().replace('\\', '/');
      try (InputStream sharedLibraryResourceStream = steam_api.class.getClassLoader().getResourceAsStream(relativeUriString)) {
         if (sharedLibraryResourceStream == null) {
            throw new IllegalStateException("Failed to find shared library " + relativeUriString + ". It was not found on the classpath.");
         }
         Path sharedLibraryRealPath = dir.resolve(sharedLibraryRelativePath).toAbsolutePath();
         Files.createDirectories(sharedLibraryRealPath.getParent());
         Files.copy(sharedLibraryResourceStream, sharedLibraryRealPath, StandardCopyOption.REPLACE_EXISTING);
      }

      System.load(dir.resolve(sharedLibraryRelativePath).toRealPath(NOFOLLOW_LINKS).toString());
   }

   /**
    * Finds the appropriate native libraries on the classpath and extracts them to {@code steamNativeDir}
    *
    * @param steamNativeDir the directory to place the extracted native shared libraries
    */
   public static synchronized void loadNativeLibrariesIntoDir(final File steamNativeDir) throws IOException {
      if (loadedLibraries) {
         return;
      }
      attemptedToLoadLibraries = true;

      final String steamApiLibraryName = "steam_api" + (isWindows && is64Bit ? "64" : "");
      extractAndLoadLibrary(steamNativeDir.toPath(), steamApiLibraryName);
      LOG.info("Steam library api loaded");
      extractAndLoadLibrary(steamNativeDir.toPath(), "SteamSdkJni");
      LOG.info("Steam library jni loaded");

      loadedLibraries = true;
   }

   public static void SteamAPI_Init(final long steamAppId) throws SteamException {
      initCalled = true;
      nSteamAPI_Init(steamAppId);
      initSuccessFul = true;
   }

   private static native void nSteamAPI_Init(long steamAppId) throws SteamException;

   public static void SteamAPI_Shutdown() throws SteamException {
      nSteamAPI_Shutdown();
   }

   private static native void nSteamAPI_Shutdown() throws SteamException;

   public static void SteamAPI_RunCallbacks() {
      nSteamAPI_RunCallbacks();
   }

   private static native void nSteamAPI_RunCallbacks();

   public static void logFromNative(final String strToLog) {
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
