package net.ttddyy.dsproxy.support.servlet;

import net.ttddyy.dsproxy.support.logging.CommonsLogger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;

/**
 * @author Niall Thomson
 */
public class CommonsQueryCountLoggingRequestListener extends QueryCountLoggingRequestListener {

    private static final String LOG_LEVEL_PARAM = "queryCountCommonsLogLevel";
    
    public void requestInitialized(ServletRequestEvent sre) {
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        final ServletContext context = sre.getServletContext();
        final String logLevelParam = context.getInitParameter(LOG_LEVEL_PARAM);
        
        this.setLogger(new CommonsLogger(logLevelParam));

        super.requestDestroyed(sre);
    }
}
