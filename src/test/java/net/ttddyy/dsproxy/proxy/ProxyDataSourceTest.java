package net.ttddyy.dsproxy.proxy;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.ttddyy.dsproxy.listener.IQueryExecutionListener;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

public class ProxyDataSourceTest {
	ProxyDataSource proxyDataSource;
	
	DataSource realDataSource;
	
	IJdbcProxyFactory proxyFactory;
	
	Connection connection;
	
	IQueryExecutionListener listener;
	
	String dsName = "datasource";
	
	@BeforeMethod
	public void beforeMethod() throws SQLException {
		connection = mock(Connection.class);
		
		proxyDataSource = new ProxyDataSource();
		
		realDataSource = mock(DataSource.class);
		when(realDataSource.getConnection()).thenReturn(connection);
		when(realDataSource.getConnection(anyString(), anyString())).thenReturn(connection);
		
		proxyFactory = mock(IJdbcProxyFactory.class);
		
		listener = mock(IQueryExecutionListener.class);
		
		proxyDataSource.setProxyFactory(proxyFactory);
		proxyDataSource.setDataSource(realDataSource);
		proxyDataSource.setDataSourceName(dsName);
		proxyDataSource.setListener(listener);
	}

	@BeforeTest
	public void beforeTest() {
	}

	@Test
	public void getConnection() throws SQLException {
		proxyDataSource.getConnection();
		
		verify(realDataSource).getConnection();
		verify(proxyFactory).createConnection(connection, listener, dsName);
	}

	@Test
	public void getConnectionLogin() throws SQLException {
		String username = "username";
		String password = "password";
		
		proxyDataSource.getConnection(username, password);
		
		verify(realDataSource).getConnection(username, password);
		verify(proxyFactory).createConnection(connection, listener, dsName);
	}

	@Test
	public void getDataSourceName() {
		assertEquals(proxyDataSource.getDataSourceName(), this.dsName);
	}

	@Test
	public void getLogWriter() throws SQLException {
		proxyDataSource.getLogWriter();
		
		verify(realDataSource).getLogWriter();
	}

	@Test
	public void getLoginTimeout() throws SQLException {
		proxyDataSource.getLoginTimeout();
		
		verify(realDataSource).getLoginTimeout();
	}

	@Test
	public void getProxyFactory() {
		IJdbcProxyFactory factory = proxyDataSource.getProxyFactory();
		
		assertTrue(factory == this.proxyFactory);
	}

	@Test
	public void isWrapperFor() throws SQLException {
		proxyDataSource.isWrapperFor(getClass());
		
		verify(realDataSource).isWrapperFor(getClass());
	}

	@Test
	public void setLogWriter() throws SQLException {
		PrintWriter printWriter = mock(PrintWriter.class);
		
		proxyDataSource.setLogWriter(printWriter);
		
		verify(realDataSource).setLogWriter(printWriter);
	}

	@Test
	public void setLoginTimeout() throws SQLException {
		proxyDataSource.setLoginTimeout(0);
		
		verify(realDataSource).setLoginTimeout(0);
	}

	@Test
	public void unwrap() throws SQLException {
		proxyDataSource.unwrap(getClass());
		
		verify(realDataSource).unwrap(getClass());
	}
}
