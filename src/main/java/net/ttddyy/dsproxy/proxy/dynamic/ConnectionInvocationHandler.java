package net.ttddyy.dsproxy.proxy.dynamic;

import net.ttddyy.dsproxy.listener.IQueryExecutionListener;
import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ObjectArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Proxy InvocationHandler for {@link java.sql.Connection}.
 *
 * @author Tadaya Tsuyukubo
 */
public class ConnectionInvocationHandler implements IJdbcProxyInvocationHandler {

    private static final Set<String> JDBC4_METHODS = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList("unwrap", "isWrapperFor"))
    );


    private Connection connection;
    private IQueryExecutionListener listener;
    private String dataSourceName;

	private IJdbcProxyFactory proxyFactory;

    public ConnectionInvocationHandler(
            Connection connection, IQueryExecutionListener listener, String dataSourceName) {
        this.connection = connection;
        this.listener = listener;
        this.dataSourceName = dataSourceName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final String methodName = method.getName();

        if ("toString".equals(methodName)) {
            StringBuilder sb = new StringBuilder("ConnectionInvocationHandler [");
            sb.append(connection.toString());
            sb.append("]");
            return sb.toString(); // differentiate toString message.
        } else if ("getDataSourceName".equals(methodName)) {
            return dataSourceName;
        } else if ("getTarget".equals(methodName)) {
            // ProxyJdbcObject interface has method to return original object.
            return connection;
        }

        if (JDBC4_METHODS.contains(methodName)) {
            final Class<?> clazz = (Class<?>) args[0];
            if ("unwrap".equals(methodName)) {
                return connection.unwrap(clazz);
            } else if ("isWrapperFor".equals(methodName)) {
                return connection.isWrapperFor(clazz);
            }
        }

        // Invoke method on original Connection.
        try {
            Object retVal = method.invoke(connection, args);

            // when it is a call to createStatement or prepareStaement, or prepareCall return proxy
            // most of the time, spring and hibernate use prepareStatement to execute query as batch
            if ("createStatement".equals(methodName)) {
                return proxyFactory.createStatement((Statement) retVal, listener, dataSourceName);
            } else if ("prepareStatement".equals(methodName)) {
                if (ObjectArrayUtils.isFirstArgString(args)) {
                    final String query = (String) args[0];
                    return proxyFactory.createPreparedStatement((PreparedStatement) retVal, query,
                            listener, dataSourceName);
                }
            } else if ("prepareCall".equals(methodName)) {  // for stored procedure call
                if (ObjectArrayUtils.isFirstArgString(args)) {
                    final String query = (String) args[0];
                    return proxyFactory.createCallableStatement((CallableStatement) retVal, query,
                            listener, dataSourceName);
                }
            }

            return retVal;
        }
        catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }


    }

	public void setProxyFactory(IJdbcProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

}
