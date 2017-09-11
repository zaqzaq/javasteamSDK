package steam;

import java.nio.ByteBuffer;

public class ISteamUtils {
	public enum ENotificationPosition {
		topLeft, topRight, bottomLeft, bottomRight
	}

	public static final long GetAppID() {
		return nGetAppID();
	}

	private static final native long nGetAppID();

	public static void setOverlayNotificationPosition(ENotificationPosition pos) {
		nSetOverlayNotificationPosition(pos.ordinal());
	}
	private static native void nSetOverlayNotificationPosition(int pos);
	

	public static boolean GetImageSize(int iImage, SteamImageDimensions steamImageDimensions){
		return nGetImageSize(iImage, steamImageDimensions);
	}
	private static native boolean nGetImageSize(int iImage, SteamImageDimensions steamImageDimensions);
	
	public static boolean GetImageRGBA(int iImage, ByteBuffer pubDest){
		return nGetImageRGBA(iImage, pubDest);
	}
	private static native boolean nGetImageRGBA(int iImage, ByteBuffer pubDest);
}
