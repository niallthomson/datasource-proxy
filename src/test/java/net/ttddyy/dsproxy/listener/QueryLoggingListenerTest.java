package net.ttddyy.dsproxy.listener;

import java.util.ArrayList;
import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.support.logging.ILogger;

import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class QueryLoggingListenerTest {
	private QueryLoggingListener queryLoggingListener;
	private ILogger logger;
	private ExecutionInfo execInfo;
	private List<QueryInfo> queryInfoList;
	private Object result;
	private LogMessage expectedLogMessage;
	private String dsName = "test";
	private long elapsedTime;
	
	@BeforeMethod
	public void beforeTest() {
		result = new Object();

		logger = mock(ILogger.class);
		
		queryLoggingListener = new QueryLoggingListener(logger);
		
		expectedLogMessage = new LogMessage("test", result, 5, "{SELECT * FROM test WHERE id=1} ", 1);
	}

	@Test
	public void afterQuery() {
		List<String> queryArgs = new ArrayList<String>();
		queryArgs.add("1");
		
		QueryInfo query = new QueryInfo();
		query.setQuery("SELECT * FROM test WHERE id=?");
		query.setQueryArgs(queryArgs);
		
		queryInfoList = new ArrayList<QueryInfo>();
		queryInfoList.add(query);
		
		execInfo = new ExecutionInfo();
		execInfo.setDataSourceName("test");
		execInfo.setElapsedTime(5);
		execInfo.setResult(result);
		
		setup(dsName, elapsedTime, "{SELECT * FROM test WHERE id=1} ", 1);
		
		queryLoggingListener.afterQuery(execInfo, queryInfoList);
		
		ArgumentCaptor<LogMessage> logMessageCaptor = ArgumentCaptor.forClass(LogMessage.class);
		
		verify(logger).log(logMessageCaptor.capture());
		
		assertTrue(logMessageCaptor.getValue().equals(expectedLogMessage));
	}
	
	@Test
	public void afterQueryMultiple() {
		queryInfoList = new ArrayList<QueryInfo>();
		
		List<String> queryArgs = new ArrayList<String>();
		queryArgs.add("1");
		
		QueryInfo query = new QueryInfo();
		query.setQuery("SELECT * FROM test WHERE id=?");
		query.setQueryArgs(queryArgs);
		
		queryInfoList.add(query);
		
		queryArgs = new ArrayList<String>();
		queryArgs.add("5");
		
		query = new QueryInfo();
		query.setQuery("SELECT * FROM test1 WHERE id=?");
		query.setQueryArgs(queryArgs);
		
		queryInfoList.add(query);
		
		execInfo = new ExecutionInfo();
		execInfo.setDataSourceName("test");
		execInfo.setElapsedTime(5);
		execInfo.setResult(result);
		
		setup(dsName, elapsedTime, "{SELECT * FROM test WHERE id=1} {SELECT * FROM test1 WHERE id=5} ", 2);
		
		queryLoggingListener.afterQuery(execInfo, queryInfoList);
		
		ArgumentCaptor<LogMessage> logMessageCaptor = ArgumentCaptor.forClass(LogMessage.class);
		
		verify(logger).log(logMessageCaptor.capture());
		
		assertTrue(logMessageCaptor.getValue().equals(expectedLogMessage));
	}
	
	private void setup(String dataSourceName, long elapsedTime, String query, int numQueries) {
		execInfo = new ExecutionInfo();
		execInfo.setDataSourceName(dataSourceName);
		execInfo.setElapsedTime(elapsedTime);
		execInfo.setResult(result);
		
		expectedLogMessage = new LogMessage(dataSourceName, result, elapsedTime, query, numQueries);
	}
}
