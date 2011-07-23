package net.ttddyy.dsproxy.proxy.dynamic;

import java.lang.reflect.InvocationHandler;

import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;

public interface IJdbcProxyInvocationHandler extends InvocationHandler {
	public void setProxyFactory(IJdbcProxyFactory proxyFactory);
}
