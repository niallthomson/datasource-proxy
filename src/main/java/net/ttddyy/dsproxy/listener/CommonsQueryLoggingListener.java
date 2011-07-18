package net.ttddyy.dsproxy.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Log executed query information using Commons-Logging.
 *
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public class CommonsQueryLoggingListener extends AbstractLoggingListener {
	private Log log = LogFactory.getLog(CommonsQueryLoggingListener.class);
    private CommonsLogLevel logLevel = CommonsLogLevel.DEBUG;
    
    public CommonsQueryLoggingListener(String messageFormat) {
		super(messageFormat);
	}

    public void log(String message) {
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

    public void setLogLevel(CommonsLogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
