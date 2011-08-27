package net.ttddyy.dsproxy.support.servlet;

import net.ttddyy.dsproxy.support.logging.SLF4JLogger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;

/**
 * @author Niall Thomson
 */
public class SLF4JQueryCountLoggingRequestListener extends QueryCountLoggingRequestListener {

    private static final String LOG_LEVEL_PARAM = "queryCountSLF4JLogLevel";

    public void requestInitialized(ServletRequestEvent sre) {
    }

    public void requestDestroyed(ServletRequestEvent sre) {
    	final ServletContext context = sre.getServletContext();
        final String logLevelParam = context.getInitParameter(LOG_LEVEL_PARAM);
        
        this.setLogger(new SLF4JLogger(logLevelParam));

        super.requestDestroyed(sre);
    }
}
