package net.ttddyy.dsproxy.proxy.dynamic;

import net.ttddyy.dsproxy.listener.IQueryExecutionListener;
import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Proxy InvocationHandler for {@link javax.sql.DataSource}.
 *
 * @author Tadaya Tsuyukubo
 */
public class DataSourceInvocationHandler implements IJdbcProxyInvocationHandler {

    private static final Set<String> JDBC4_METHODS = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList("unwrap", "isWrapperFor"))
    );

    private DataSource dataSource;
    private IQueryExecutionListener listener;
    private String dataSourceName;
    private IJdbcProxyFactory proxyFactory;

    public DataSourceInvocationHandler() {
    }

    public DataSourceInvocationHandler(DataSource dataSource, IQueryExecutionListener listener, String dataSourceName) {
        this.dataSource = dataSource;
        this.listener = listener;
        this.dataSourceName = dataSourceName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final String methodName = method.getName();

        if ("toString".equals(methodName)) {
            StringBuilder sb = new StringBuilder("DataSourceInvocationHandler [");
            sb.append(dataSource.toString());
            sb.append("]");
            return sb.toString(); // differentiate toString message.
        } else if ("getDataSourceName".equals(methodName)) {
            return dataSourceName;
        } else if ("getTarget".equals(methodName)) {
            // ProxyJdbcObject interface has method to return original object.
            return dataSource;
        }

        if (JDBC4_METHODS.contains(methodName)) {
            final Class<?> clazz = (Class<?>) args[0];
            if ("unwrap".equals(methodName)) {
                return dataSource.unwrap(clazz);
            } else if ("isWrapperFor".equals(methodName)) {
                return dataSource.isWrapperFor(clazz);
            }
        }

        // Invoke method on original datasource.
        try {
            final Object retVal = method.invoke(dataSource, args);

            if ("getConnection".equals(methodName)) {
                return proxyFactory.createConnection((Connection) retVal, listener, dataSourceName);
            }
            return retVal;
        }


        catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setListener(IQueryExecutionListener listener) {
        this.listener = listener;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

	public void setProxyFactory(IJdbcProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}
}
