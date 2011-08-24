package net.ttddyy.dsproxy.support.logging;

import net.ttddyy.dsproxy.util.BeanFormatter;

public abstract class AbstractLogger implements ILogger {
	private BeanFormatter formatter;

	public void setFormat(String format) {
		this.setFormatter(new BeanFormatter(format));
	}
	
	protected void setFormatter(BeanFormatter formatter) {
		this.formatter = formatter;
	}

	public void log(Object message) {
		try {
			writeLog(formatter.format(message));
		} catch (Exception e) {
			writeLog("Failed to format log message");
			
			e.printStackTrace();
		}
	}
	
	protected abstract void writeLog(String message);
}
