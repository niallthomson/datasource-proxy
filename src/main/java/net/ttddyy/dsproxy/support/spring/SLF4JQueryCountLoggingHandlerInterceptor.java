package net.ttddyy.dsproxy.support.spring;

import net.ttddyy.dsproxy.support.logging.SLF4JLogger;

public class SLF4JQueryCountLoggingHandlerInterceptor extends QueryCountLoggingHandlerInterceptor {
	public SLF4JQueryCountLoggingHandlerInterceptor() {
		this.setLogger(new SLF4JLogger());
	}
}
