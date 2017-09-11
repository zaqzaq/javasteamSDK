package steam;

public class DefaultSteamLogger implements SteamLogger {

	public enum Level {
		ERROR, WARN, INFO, DEBUG, TRACE, OFF;
	}

	private void log(Level level, String message, Throwable t) {
		if (message != null) {
			if (level.ordinal() < Level.INFO.ordinal()) {
				System.err.println(message);
			} else {
				System.out.println(message);
			}
		}
		if (t != null) {
			t.printStackTrace();
		}
	}

	public void trace(String message) {
		log(Level.TRACE, message, null);
	}

	public void trace(String message, Throwable t) {
		log(Level.TRACE, message, t);
	}

	public void debug(String message) {
		log(Level.DEBUG, message, null);
	}

	public void debug(String message, Throwable t) {
		log(Level.DEBUG, message, t);
	}

	public void info(String message) {
		log(Level.INFO, message, null);
	}

	public void info(String message, Throwable t) {
		log(Level.INFO, message, t);
	}

	public void warn(String message) {
		log(Level.WARN, message, null);
	}

	public void warn(String message, Throwable t) {
		log(Level.WARN, message, t);
	}

	public void error(String message) {
		log(Level.ERROR, message, null);
	}

	public void error(String message, Throwable t) {
		log(Level.ERROR, message, t);
	}

}
