package net.ttddyy.dsproxy.proxy;

import net.ttddyy.dsproxy.proxy.dynamic.CallableStatementInvocationHandler;
import net.ttddyy.dsproxy.proxy.dynamic.ConnectionInvocationHandler;
import net.ttddyy.dsproxy.proxy.dynamic.JdbcDynamicProxyFactory;
import net.ttddyy.dsproxy.proxy.dynamic.PreparedStatementInvocationHandler;
import net.ttddyy.dsproxy.proxy.dynamic.StatementInvocationHandler;

/**
 * Proxy object implements this interface to provide a method to return wrapped object.
 *
 * @author Tadaya Tsuyukubo
 *
 * @see JdbcDynamicProxyFactory
 * @see ConnectionInvocationHandler
 * @see StatementInvocationHandler
 * @see PreparedStatementInvocationHandler
 * @see CallableStatementInvocationHandler
 */
public interface ProxyJdbcObject {

    /**
     * Method to return wrapped source object(Connection, Statement, PreparedStatement, CallableStatement).
     * @return source object
     */
    Object getTarget();
}
