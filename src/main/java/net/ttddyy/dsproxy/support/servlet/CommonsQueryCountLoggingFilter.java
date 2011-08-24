package net.ttddyy.dsproxy.support.servlet;

import net.ttddyy.dsproxy.support.logging.CommonsLogger;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public class CommonsQueryCountLoggingFilter extends QueryCountLoggingFilter {
	public void init(FilterConfig filterConfig) throws ServletException {
        this.setLogger(new CommonsLogger());
    }
}
