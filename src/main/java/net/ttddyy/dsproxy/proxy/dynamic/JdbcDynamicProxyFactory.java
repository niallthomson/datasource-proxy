package net.ttddyy.dsproxy.proxy.dynamic;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.IJdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ProxyJdbcObject;

import javax.sql.DataSource;

import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Factory class to return a proxy with InvocationHandler used by
 * datasource-proxy.
 * 
 * @author Tadaya Tsuyukubo
 * @author Niall Thomson
 */
public class JdbcDynamicProxyFactory implements IJdbcProxyFactory {

	public DataSource createDataSource(DataSource dataSource,
			QueryExecutionListener listener, String dataSourceName) {
		return (DataSource) createProxyInstance(
				DataSource.class,
				getDataSourceInvocationHandler(dataSource, listener,
						dataSourceName));
	}
	
	public Connection createConnection(Connection connection,
			QueryExecutionListener listener) {
		return createConnection(connection, listener, "");
	}

	public Connection createConnection(Connection connection,
			QueryExecutionListener listener, String dataSourceName) {
		return (Connection) createProxyInstance(
				Connection.class,
				getConnectionInvocationHandler(connection, listener,
						dataSourceName));
	}

	public Statement createStatement(Statement statement,
			QueryExecutionListener listener) {
		return createStatement(statement, listener, "");
	}

	public Statement createStatement(
			Statement statement,
			QueryExecutionListener listener, String dataSourceName) {
		return (Statement)createProxyInstance(Statement.class, getStatementInvocationHandler(statement, listener, dataSourceName));
	}

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			QueryExecutionListener listener) {
		return createPreparedStatement(preparedStatement, query, listener, "");
	}

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			QueryExecutionListener listener, String dataSourceName) {
		return (PreparedStatement)createProxyInstance(PreparedStatement.class, getPreparedStatementInvocationHandler(preparedStatement, query, listener, dataSourceName));
	}

	public CallableStatement createCallableStatement(
			CallableStatement callableStatement, String query,
			QueryExecutionListener listener, String dataSourceName) {
		return (CallableStatement)createProxyInstance(CallableStatement.class, getCallableStatementInvocationHandler(callableStatement, query, listener, dataSourceName));
	}

	protected Object createProxyInstance(Class<?> clazz,
			IJdbcProxyInvocationHandler invocationHandler) {
		invocationHandler.setProxyFactory(this);

		return Proxy
				.newProxyInstance(ProxyJdbcObject.class.getClassLoader(),
						new Class[] { ProxyJdbcObject.class, clazz },
						invocationHandler);
	}

	protected IJdbcProxyInvocationHandler getDataSourceInvocationHandler(
			DataSource dataSource, QueryExecutionListener listener,
			String dataSourceName) {
		return new DataSourceInvocationHandler(dataSource, listener,
				dataSourceName);
	}

	protected IJdbcProxyInvocationHandler getConnectionInvocationHandler(
			Connection connection, QueryExecutionListener listener,
			String dataSourceName) {
		return new ConnectionInvocationHandler(connection, listener,
				dataSourceName);
	}
	
	protected IJdbcProxyInvocationHandler getPreparedStatementInvocationHandler(
			PreparedStatement preparedStatement, String query,
			QueryExecutionListener listener, String dataSourceName) {
		return new PreparedStatementInvocationHandler(preparedStatement, query, listener,
				dataSourceName);
	}
	
	protected IJdbcProxyInvocationHandler getCallableStatementInvocationHandler(
			CallableStatement callableStatement, String query,
			QueryExecutionListener listener, String dataSourceName) {
		return new CallableStatementInvocationHandler(callableStatement, query, listener,
				dataSourceName);
	}
	
	protected IJdbcProxyInvocationHandler getStatementInvocationHandler(
			Statement statement,
			QueryExecutionListener listener, String dataSourceName) {
		return new StatementInvocationHandler(statement, listener,
				dataSourceName);
	}
}
