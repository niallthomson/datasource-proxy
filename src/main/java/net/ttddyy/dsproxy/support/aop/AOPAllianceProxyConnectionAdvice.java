package net.ttddyy.dsproxy.support.aop;

import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.sql.Connection;

/**
 * Support injecting proxies through 'AOP Alliance' AOP. 
 *
 * @author Tadaya Tsuyukubo
 */
public class AOPAllianceProxyConnectionAdvice implements MethodInterceptor {
	private IJdbcProxyFactory proxyFactory;
	
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object retVal = invocation.proceed();

        // only when return value is connection, return proxy.
        if (!(retVal instanceof Connection)) {
            return retVal;
        }

        return this.proxyFactory.createConnection((Connection) retVal, null);
    }

	public void setProxyFactory(IJdbcProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

	public IJdbcProxyFactory getProxyFactory() {
		return this.proxyFactory;
	}

}
