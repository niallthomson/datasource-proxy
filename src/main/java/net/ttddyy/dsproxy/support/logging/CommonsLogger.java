package net.ttddyy.dsproxy.support.logging;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Log executed query information using Commons-Logging.
 *
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public class CommonsLogger extends AbstractLogger {
	private Log log = LogFactory.getLog(CommonsLogger.class);
    private CommonsLogLevel logLevel = CommonsLogLevel.DEBUG;

    public void setLogLevel(CommonsLogLevel logLevel) {
        this.logLevel = logLevel;
    }

	@Override
	public void writeLog(String message) {
		switch (logLevel) {
	        case DEBUG:
	            log.debug(message);
	            break;
	        case ERROR:
	            log.error(message);
	            break;
	        case FATAL:
	            log.fatal(message);
	            break;
	        case INFO:
	            log.info(message);
	            break;
	        case TRAC:
	            log.trace(message);
	            break;
	        case WARN:
	            log.warn(message);
	            break;
	    }
	}

	public void setLogLevel(String level) {
		final CommonsLogLevel logLevel = CommonsLogLevel.nullSafeValueOf(level);
		
        if (logLevel != null) {
            this.logLevel = logLevel;
        }
	}
}
