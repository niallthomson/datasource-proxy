package net.ttddyy.dsproxy.support.logging;


public class SimpleLogger extends AbstractLogger {
	@Override
	public void writeLog(String message) {
		System.out.println(message);
	}

	public void setLogLevel(String level) {
		// Simple logger doesn't handle log level
	}
}
