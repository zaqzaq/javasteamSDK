/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam.exception;

public class SteamException extends Exception {

	public SteamException() {
		super();
	}

	public SteamException(String message, Throwable cause) {
		super(message, cause);
	}

	public SteamException(String message) {
		super(message);
	}

	public SteamException(Throwable cause) {
		super(cause);
	}
}
