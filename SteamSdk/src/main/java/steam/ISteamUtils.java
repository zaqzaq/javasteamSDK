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
