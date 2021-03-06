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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TestUserAvatar extends TestCase {
   private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   @Override
   protected void setUp() throws Exception {
      try {
         LOG.info("setUp: initializing Steam.");
         steam_api.loadNativeLibrariesIntoDir(new File("build/dist"));
         steam_api.SteamAPI_Init(steam_apiTest.STEAM_APP_ID_TEST);
      } catch (final Exception ex) {
         ex.printStackTrace();
         throw ex;
      }
   }

   @Override
   @SuppressWarnings("deprecation")
   protected void tearDown() throws Exception {
      try {
         steam_api.SteamAPI_Shutdown();
      } catch (final Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   @Test
   public void testAvatarCatch() throws Throwable {
      try {
         avatar();
      } catch (final Throwable t) {
         t.printStackTrace();
         throw t;
      }
   }

   public void avatar() throws IOException {
      final CSteamID steamID = ISteamUser.GetSteamID();
      final int imageID = ISteamFriends.GetSmallFriendAvatar(steamID);
      System.out.println("imageID = " + imageID);
      final SteamImageDimensions steamImageDimensions = new SteamImageDimensions(0, 0);
      ISteamUtils.GetImageSize(imageID, steamImageDimensions);
      System.out.println("Image dimensions are width = " + steamImageDimensions.getWidth() + " height = " + steamImageDimensions.getWidth());
      final ByteBuffer imageBuffer =
		      ByteBuffer.allocateDirect(4 * steamImageDimensions.getWidth() * steamImageDimensions.getHeight()).order(ByteOrder.nativeOrder());
      final boolean gotImage = ISteamUtils.GetImageRGBA(imageID, imageBuffer);
      System.out.println("Got image = " + gotImage);
      final BufferedImage image = new BufferedImage(steamImageDimensions.getWidth(), steamImageDimensions.getHeight(), BufferedImage.TYPE_INT_ARGB);

      imageBuffer.clear();
      for (int i = 0; i < steamImageDimensions.getWidth() * steamImageDimensions.getHeight(); i++) {
         final int r = (imageBuffer.get() & 0xFF) << 16;
         final int g = (imageBuffer.get() & 0xFF) << 8;
         final int b = (imageBuffer.get() & 0xFF) << 0;
         final int a = (imageBuffer.get() & 0xFF) << 24;
         image.setRGB(i % steamImageDimensions.getWidth(), i / steamImageDimensions.getWidth(), a | r | g | b);
      }
      ImageIO.write(image, "png", new File("avatar.png"));
   }
}
