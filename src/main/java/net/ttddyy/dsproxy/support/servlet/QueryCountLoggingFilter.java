package net.ttddyy.dsproxy.support.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.ttddyy.dsproxy.support.logging.AbstractQueryCountLogger;

/**
 * Servlet Filter to log the query metrics during a http request lifecycle.
 *
 * Some application server reuse threads without cleaning thread local values. Default, this filter clear
 * the thread local value used to hold the query metrics. If you do not want to clear the thread local value,
 * set "clearQueryCounter", a servlet parameter, to false.
 *
 * Also, you can set a log level.  
 *
 * @author Tadaya Tsuyukubo
 * @author Niall Thomson
 * @see QueryCountLoggingHandlerInterceptor
 * @see QueryCountLoggingRequestListener
 */
public class QueryCountLoggingFilter extends AbstractQueryCountLogger implements Filter {
	private static final String CLEAR_QUERY_COUNTER_PARAM = "clearQueryCounter";
	private static final String LOG_LEVEL_PARAM = "logLevel";

    public void init(FilterConfig filterConfig) throws ServletException {
        final String clearQueryCounterParam = filterConfig.getInitParameter(CLEAR_QUERY_COUNTER_PARAM);
        if (clearQueryCounterParam != null && "false".equalsIgnoreCase(clearQueryCounterParam)) {
            this.setClearQueryCounter(false);
        }
        
        final String logLevelParam = filterConfig.getInitParameter(LOG_LEVEL_PARAM);  
        if(logLevelParam != null) {
        	this.setLogLevel(logLevelParam);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        this.logQueryCount();
    }

	public void destroy() {
		// Do nothing
	}
}
