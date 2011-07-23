package net.ttddyy.dsproxy.proxy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;

public interface IJdbcProxyFactory {

	public DataSource createDataSource(DataSource dataSource,
			QueryExecutionListener listener, String dataSourceName);

	public Connection createConnection(Connection connection,
			QueryExecutionListener listener);

	public Connection createConnection(Connection connection,
			QueryExecutionListener listener, String dataSourceName);

	public Statement createStatement(Statement statement,
			QueryExecutionListener listener);

	public Statement createStatement(Statement statement,
			QueryExecutionListener listener, String dataSourceName);

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			QueryExecutionListener listener);

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			QueryExecutionListener listener, String dataSourceName);

	public CallableStatement createCallableStatement(
			CallableStatement callableStatement, String query,
			QueryExecutionListener listener, String dataSourceName);

}