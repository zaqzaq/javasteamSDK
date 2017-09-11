package steam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import org.junit.Test;

public class TestUserAvatar extends TestCase {
	
	private static Thread callbackThread;
	
	protected void setUp() throws Exception {
		try{
			steam_api.getLogger().info("setUp: initializing Steam.");
			steam_api.loadNativeLibrariesFromJavaLibraryPath();
			steam_api.SteamAPI_Init(steam_apiTest.STEAM_APP_ID_TEST);
			callbackThread = startRunCallbackThread();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("deprecation")
	protected void tearDown() throws Exception {
		try{
			callbackThread.stop();
			steam_api.SteamAPI_Shutdown();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testAvatarCatch() throws Throwable{
		try{
			avatar();
		}catch(Throwable t){
			t.printStackTrace();
			throw t;
		}
	}
	
	
	public void avatar() throws IOException{
		CSteamID steamID = ISteamUser.GetSteamID();
		int imageID = ISteamFriends.GetSmallFriendAvatar(steamID);
		System.out.println("imageID = " + imageID);
		SteamImageDimensions steamImageDimensions = new SteamImageDimensions(0, 0);
		ISteamUtils.GetImageSize(imageID, steamImageDimensions);
		System.out.println("Image dimensions are width = " + steamImageDimensions.getWidth() + " height = " + steamImageDimensions.getWidth());
		ByteBuffer imageBuffer = ByteBuffer.allocateDirect(4 * (steamImageDimensions.getWidth()*steamImageDimensions.getHeight())).order(ByteOrder.nativeOrder());
		boolean gotImage = ISteamUtils.GetImageRGBA(imageID, imageBuffer);
		System.out.println("Got image = " + gotImage);
		BufferedImage image = new BufferedImage(steamImageDimensions.getWidth(), steamImageDimensions.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		imageBuffer.clear();
		for(int i = 0; i < steamImageDimensions.getWidth() * steamImageDimensions.getHeight(); i++){
			int r = ((imageBuffer.get())&0xFF)<<16;
			int g = ((imageBuffer.get())&0xFF)<< 8;
			int b = ((imageBuffer.get())&0xFF)<< 0;
			int a = ((imageBuffer.get())&0xFF)<<24;
			image.setRGB(i%steamImageDimensions.getWidth(), i/steamImageDimensions.getWidth(), a|r|g|b);
		}
		ImageIO.write(image, "png", new File("avatar.png"));
	}
	
	private static Thread startRunCallbackThread() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					steam_api.SteamAPI_RunCallbacks();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		return thread;
	}

}
