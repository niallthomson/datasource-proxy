package net.ttddyy.dsproxy.support.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.ttddyy.dsproxy.support.logging.SLF4JLogger;

/**
 * @author Niall Thomson
 */
public class SLF4JQueryCountLoggingFilter extends QueryCountLoggingFilter {
	public void init(FilterConfig filterConfig) throws ServletException {
        this.setLogger(new SLF4JLogger());
        
        super.init(filterConfig);
    }
}
