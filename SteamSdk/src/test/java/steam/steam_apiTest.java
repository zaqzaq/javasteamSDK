/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

package steam;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

public class steam_apiTest extends TestCase {

   public static long STEAM_APP_ID_TEST = 480;

   @Override
   protected void setUp() throws Exception {
      steam_api.loadNativeLibrariesIntoDir(new File("build/dist"));
      steam_api.SteamAPI_Init(STEAM_APP_ID_TEST);
   }

   @Override
   @SuppressWarnings("deprecation")
   protected void tearDown() throws Exception {
      steam_api.SteamAPI_Shutdown();
   }

   @Test
   public void testFriends() {
      final CSteamID mySteamID = ISteamUser.GetSteamID();
      System.out.println("my steam id is " + ISteamFriends.GetPersonaName() + " (" + mySteamID + ")");
      System.out.println("Friends:");
      final int numberOfFriends = ISteamFriends.GetFriendCount(EFriendFlags.k_EFriendFlagImmediate);
      System.out.println("I have " + numberOfFriends + " friends.");
      for (int i = 0; i < numberOfFriends; i++) {
         final CSteamID myFriendSteamID = ISteamFriends.GetFriendByIndex(i, EFriendFlags.k_EFriendFlagImmediate);
         final String friendName = ISteamFriends.GetFriendPersonaName(myFriendSteamID);
         System.out.println(friendName + " (" + myFriendSteamID + ")");
      }
   }

   @Test
   public void testSteamAPI() {
      try {
         privateTestSteamAPI();
      } catch (final Throwable t) {
         t.printStackTrace();
         throw t;
      }
   }

   private void privateTestSteamAPI() {
      final boolean requestCurrentStats = ISteamUserStats.RequestCurrentStats();
      System.out.println("requestCurrentStats returned " + requestCurrentStats);

      readAchievements();
      setAchievements();
      readAchievements();
      if (true) {
         clearAchievements();
         readAchievements();
      }
      ISteamUserStats.StoreStats();
      System.out.println("sleeping 5 seconds to allow storage to propagate");
      try {
         Thread.sleep(5000);
      } catch (final Exception e) {
      }
   }

   public static final int[] ACHS_IDS = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 60, 61, 62, 63, 64, 65};
   public static final String[] ACHS = new String[ACHS_IDS.length];// { "ACH_WIN_ONE_GAME", "ACH_WIN_100_GAMES", "ACH_TRAVEL_FAR_ACCUM",
	// "ACH_TRAVEL_FAR_SINGLE" };

   static {
      for (int i = 0; i < ACHS_IDS.length; i++) {
         ACHS[i] = String.valueOf(ACHS_IDS[i]);
      }
   }

   private void clearAchievements() {
      for (int i = 0; i < ACHS.length; i++) {
         final boolean achBoolean = ISteamUserStats.ClearAchievement(ACHS[i]);
         System.out.println("clear " + ACHS[i] + " = " + achBoolean);
      }
   }

   private void setAchievements() {
      for (int i = 0; i < ACHS.length; i++) {
         final boolean achBoolean = ISteamUserStats.SetAchievement(ACHS[i]);
         System.out.println("set " + ACHS[i] + " = " + achBoolean);
      }
   }

   private void readAchievements() {
      for (int i = 0; i < ACHS.length; i++) {
         final boolean achBoolean = ISteamUserStats.GetAchievement(ACHS[i]);
         System.out.println("get " + ACHS[i] + " = " + achBoolean);
      }
   }
}
