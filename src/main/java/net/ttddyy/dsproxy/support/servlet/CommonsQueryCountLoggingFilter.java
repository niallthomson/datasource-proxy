package net.ttddyy.dsproxy.support.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.ttddyy.dsproxy.support.logging.CommonsLogger;

/**
 * @author Niall Thomson
 */
public class CommonsQueryCountLoggingFilter extends QueryCountLoggingFilter {
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setLogger(new CommonsLogger());
		
		super.init(filterConfig);
	}
}
