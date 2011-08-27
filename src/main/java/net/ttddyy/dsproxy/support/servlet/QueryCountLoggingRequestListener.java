package net.ttddyy.dsproxy.support.servlet;

import net.ttddyy.dsproxy.support.logging.AbstractQueryCountLogger;
import net.ttddyy.dsproxy.support.servlet.CommonsQueryCountLoggingFilter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * {@link javax.servlet.ServletRequestListener} to log the query metrics during a http request lifecycle.
 *
 * @author Niall Thomson
 * @see CommonsQueryCountLoggingFilter
 * @see CommonsQueryCountLoggingHandlerInterceptor
 */
public class QueryCountLoggingRequestListener extends AbstractQueryCountLogger implements ServletRequestListener { 
    public void requestInitialized(ServletRequestEvent sre) {
    	// Do nothing
    }

    public void requestDestroyed(ServletRequestEvent sre) {
    	this.setClearQueryCounter(true);
        this.logQueryCount();
    }
}
