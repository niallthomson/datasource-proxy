package net.ttddyy.dsproxy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log executed query information using SLF4J.
 *
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public class SLF4JQueryLoggingListener extends AbstractLoggingListener {
	private Logger logger = LoggerFactory.getLogger(SLF4JQueryLoggingListener.class);
    private SLF4JLogLevel logLevel = SLF4JLogLevel.DEBUG;
    
    public SLF4JQueryLoggingListener(String messageFormat) {
		super(messageFormat);
	}

    public void log(String message) {
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

    public void setLogLevel(SLF4JLogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
