package net.ttddyy.dsproxy.support.logging;

public interface ILogger {
	public void log(Object message);
	public void setFormat(String format);
	public void setLogLevel(String level);
}
