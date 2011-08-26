package net.ttddyy.dsproxy.proxy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import net.ttddyy.dsproxy.listener.IQueryExecutionListener;

public interface IJdbcProxyFactory {

	public DataSource createDataSource(DataSource dataSource,
			IQueryExecutionListener listener, String dataSourceName);

	public Connection createConnection(Connection connection,
			IQueryExecutionListener listener);

	public Connection createConnection(Connection connection,
			IQueryExecutionListener listener, String dataSourceName);

	public Statement createStatement(Statement statement,
			IQueryExecutionListener listener);

	public Statement createStatement(Statement statement,
			IQueryExecutionListener listener, String dataSourceName);

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			IQueryExecutionListener listener);

	public PreparedStatement createPreparedStatement(
			PreparedStatement preparedStatement, String query,
			IQueryExecutionListener listener, String dataSourceName);

	public CallableStatement createCallableStatement(
			CallableStatement callableStatement, String query,
			IQueryExecutionListener listener, String dataSourceName);

}