package net.ttddyy.dsproxy.support.spring;

import net.ttddyy.dsproxy.support.logging.CommonsLogger;

public class CommonsQueryCountLoggingHandlerInterceptor extends QueryCountLoggingHandlerInterceptor {
	public CommonsQueryCountLoggingHandlerInterceptor() {
		this.setLogger(new CommonsLogger());
	}
}
