package steam;

public enum EServerMode {
	eServerModeInvalid(0), // DO NOT USE		
	eServerModeNoAuthentication(1), // Don't authenticate user logins and don't list on the server list
	eServerModeAuthentication(2), // Authenticate users, list on the server list, don't run VAC on clients that connect
	eServerModeAuthenticationAndSecure(3);
	
	public final int toInt;
	private EServerMode(int mode){
		this.toInt = mode;
	}
}
