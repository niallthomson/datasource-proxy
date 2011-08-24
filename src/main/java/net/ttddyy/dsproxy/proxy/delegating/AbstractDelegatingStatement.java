package net.ttddyy.dsproxy.proxy.delegating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public abstract class AbstractDelegatingStatement<S extends Statement> implements Statement {
	
	private S delegate;
	
	public AbstractDelegatingStatement(S delegate) {
		this.delegate = delegate;
	}

	public S getDelegate() {
		return this.delegate;
	}
	
	public void setDelegate(S delegate) {
		this.delegate = delegate;
	}

	public boolean isWrapperFor(Class<?> clazz) throws SQLException {
		return this.getDelegate().isWrapperFor(clazz);
	}

	public <T> T unwrap(Class<T> clazz) throws SQLException {
		return this.getDelegate().unwrap(clazz);
	}

	public void addBatch(String arg0) throws SQLException {
		this.getDelegate().addBatch(arg0);
	}

	public void cancel() throws SQLException {
		this.getDelegate().cancel();
	}

	public void clearBatch() throws SQLException {
		this.getDelegate().clearBatch();
	}

	public void clearWarnings() throws SQLException {
		this.getDelegate().clearWarnings();
	}

	public void close() throws SQLException {
		this.getDelegate().close();
	}

	public boolean execute(String arg0) throws SQLException {
		return this.getDelegate().execute(arg0);
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		return this.getDelegate().execute(arg0, arg1);
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		return this.getDelegate().execute(arg0, arg1);
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		return this.getDelegate().execute(arg0, arg1);
	}

	public int[] executeBatch() throws SQLException {
		return this.getDelegate().executeBatch();
	}

	public ResultSet executeQuery(String arg0) throws SQLException {
		return this.getDelegate().executeQuery(arg0);
	}

	public int executeUpdate(String arg0) throws SQLException {
		return this.getDelegate().executeUpdate(arg0);
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		return this.getDelegate().executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		return this.getDelegate().executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		return this.getDelegate().executeUpdate(arg0, arg1);
	}

	public Connection getConnection() throws SQLException {
		return this.getDelegate().getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return this.getDelegate().getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return this.getDelegate().getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return this.getDelegate().getGeneratedKeys();
	}

	public int getMaxFieldSize() throws SQLException {
		return this.getDelegate().getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return this.getDelegate().getMaxRows();
	}

	public boolean getMoreResults() throws SQLException {
		return this.getDelegate().getMoreResults();
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		return this.getDelegate().getMoreResults(arg0);
	}

	public int getQueryTimeout() throws SQLException {
		return this.getDelegate().getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		return this.getDelegate().getResultSet();
	}

	public int getResultSetConcurrency() throws SQLException {
		return this.getDelegate().getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return this.getDelegate().getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return this.getDelegate().getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return this.getDelegate().getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.getDelegate().getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return this.getDelegate().isClosed();
	}

	public boolean isPoolable() throws SQLException {
		return this.getDelegate().isPoolable();
	}

	public void setCursorName(String arg0) throws SQLException {
		this.getDelegate().setCursorName(arg0);
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		this.getDelegate().setEscapeProcessing(arg0);
	}

	public void setFetchDirection(int arg0) throws SQLException {
		this.getDelegate().setFetchDirection(arg0);
	}

	public void setFetchSize(int arg0) throws SQLException {
		this.getDelegate().setFetchSize(arg0);
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		this.getDelegate().setMaxFieldSize(arg0);
	}

	public void setMaxRows(int arg0) throws SQLException {
		this.getDelegate().setMaxRows(arg0);
	}

	public void setPoolable(boolean arg0) throws SQLException {
		this.getDelegate().setPoolable(arg0);
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		this.setQueryTimeout(arg0);
	}
}
