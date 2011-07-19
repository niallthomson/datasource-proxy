package net.ttddyy.dsproxy.support;

import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.dynamic.JdbcDynamicProxyFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.sql.Connection;

/**
 * Support injecting proxies by AOP. 
 *
 * @author Tadaya Tsuyukubo
 */
public class ProxyConnectionAdvice implements MethodInterceptor {
	private IJdbcProxyFactory proxyFactory;
	
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object retVal = invocation.proceed();

        // only when return value is connection, return proxy.
        if (!(retVal instanceof Connection)) {
            return retVal;
        }

        return proxyFactory.createConnection((Connection) retVal, null);
    }

	public void setProxyFactory(IJdbcProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

	public IJdbcProxyFactory getProxyFactory() {
		return proxyFactory;
	}

}
