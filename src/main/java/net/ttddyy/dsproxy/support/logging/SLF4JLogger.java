package net.ttddyy.dsproxy.support.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log executed query information using SLF4J.
 *
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public class SLF4JLogger extends AbstractLogger {
	private Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);
    private SLF4JLogLevel logLevel = SLF4JLogLevel.DEBUG;

    public SLF4JLogger(String logLevel) {
		this.setLogLevel(logLevel);
	}
    
    public SLF4JLogger() {
		// Do nothing
	}

	public void setLogLevel(SLF4JLogLevel logLevel) {
        this.logLevel = logLevel;
    }

	@Override
	public void writeLog(String message) {
		switch (logLevel) {
	        case DEBUG:
	            logger.debug(message);
	            break;
	        case ERROR:
	            logger.error(message);
	            break;
	        case INFO:
	            logger.info(message);
	            break;
	        case TRAC:
	            logger.trace(message);
	            break;
	        case WARN:
	            logger.warn(message);
	            break;
	    }
	}

	public void setLogLevel(String level) {
		final SLF4JLogLevel logLevel = SLF4JLogLevel.nullSafeValueOf(level);
		
        if (logLevel != null) {
            this.logLevel = logLevel;
        }
	}
}
