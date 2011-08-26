package net.ttddyy.dsproxy.support.logging;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

import org.mockito.ArgumentCaptor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AbstractQueryCountLoggerTest {
	private QueryCount firstQueryCount;
	private QueryCount secondQueryCount;
	
	private List<String> dsNames;
	
	private AbstractQueryCountLogger queryCountLogger;
	
	private QueryCountHolder queryCountHolder;
	
	private ILogger logger;
	
	@BeforeTest
	public void setupTests() {
		firstQueryCount = new QueryCount(1, 2, 3, 4, 5, 6, 7, new Long(8));
		secondQueryCount = new QueryCount(11, 12, 13, 14, 15, 16, 17, new Long(18));
		
		dsNames = new ArrayList<String>();
		dsNames.add("first");
		dsNames.add("second");
		
		queryCountLogger = mock(AbstractQueryCountLogger.class);
		doCallRealMethod().when(queryCountLogger).setQueryCountHolder(any(QueryCountHolder.class));
		doCallRealMethod().when(queryCountLogger).setLogger(any(ILogger.class));
		doCallRealMethod().when(queryCountLogger).writeMessage(any(QueryCountLogMessage.class));
		doCallRealMethod().when(queryCountLogger).logQueryCount();
		doCallRealMethod().when(queryCountLogger).setClearQueryCounter(anyBoolean());
		doCallRealMethod().when(queryCountLogger).getQueryCountHolder();
		doCallRealMethod().when(queryCountLogger).setLogLevel(anyString());
		doCallRealMethod().when(queryCountLogger).setFormat(anyString());
	}

	@BeforeMethod
	public void setupTest() {
		queryCountHolder = mock(QueryCountHolder.class);
		when(queryCountHolder.getDataSourceNamesAsList()).thenReturn(dsNames);
		when(queryCountHolder.get("first")).thenReturn(firstQueryCount);
		when(queryCountHolder.get("second")).thenReturn(secondQueryCount);
		
		logger = mock(ILogger.class);
		
		queryCountLogger.setLogger(logger);
	}
	
	@AfterMethod
	public void teardownTest() {
		queryCountLogger.setQueryCountHolder(null);
	}
	
	@Test
	public void testLogQueryCountNoClear() {
		queryCountLogger.setQueryCountHolder(queryCountHolder);
		
		queryCountLogger.setClearQueryCounter(false);
		queryCountLogger.logQueryCount();
		
		verifyResult(logger, false);
	}
	
	@Test
	public void testLogQueryCountWithClear() {
		queryCountLogger.setQueryCountHolder(queryCountHolder);
		
		queryCountLogger.setClearQueryCounter(true);
		queryCountLogger.logQueryCount();
		
		verifyResult(logger, true);
	}
	
	@Test
	public void testGetQueryCountHolder() {
		queryCountLogger.setQueryCountHolder(queryCountHolder);
		
		QueryCountHolder queryCountHolder = queryCountLogger.getQueryCountHolder();
		
		assertTrue(this.queryCountHolder == queryCountHolder);
	}
	
	@Test
	public void testGetQueryCountHolderDefault() {
		QueryCountHolder queryCountHolder = queryCountLogger.getQueryCountHolder();
		
		assertTrue(QueryCountHolder.getDefaultInstance() == queryCountHolder);
	}
	
	@Test
	public void testSetLogLevel() {
		queryCountLogger.setLogLevel("level");
		
		verify(logger).setLogLevel("level");
	}
	
	@Test
	public void testSetFormat() {
		queryCountLogger.setFormat("format");
		
		verify(logger).setFormat("format");
	}
	
	private void verifyResult(ILogger logger, boolean shouldCallClear) {
		ArgumentCaptor<QueryCountLogMessage> logMessageCaptor = ArgumentCaptor.forClass(QueryCountLogMessage.class);
		
		verify(logger, times(2)).log(logMessageCaptor.capture());
		
		List<QueryCountLogMessage> messages = logMessageCaptor.getAllValues();
		
		QueryCountLogMessage firstMessage = messages.get(0);
		assertEquals(firstMessage.getDsName(), "first");
		assertEquals(firstMessage.getSelect(), 1);
		assertEquals(firstMessage.getInsert(), 2);
		assertEquals(firstMessage.getUpdate(), 3);
		assertEquals(firstMessage.getDelete(), 4);
		assertEquals(firstMessage.getOther(), 5);
		assertEquals(firstMessage.getCall(), 6);
		assertEquals(firstMessage.getFailure(), 7);
		assertEquals(firstMessage.getElapsedTime(), 8);
		assertEquals(firstMessage.getTotalNumOfQuery(), 15);
		
		QueryCountLogMessage secondMessage = messages.get(1);
		assertEquals(secondMessage.getDsName(), "second");
		assertEquals(secondMessage.getSelect(), 11);
		assertEquals(secondMessage.getInsert(), 12);
		assertEquals(secondMessage.getUpdate(), 13);
		assertEquals(secondMessage.getDelete(), 14);
		assertEquals(secondMessage.getOther(), 15);
		assertEquals(secondMessage.getCall(), 16);
		assertEquals(secondMessage.getFailure(), 17);
		assertEquals(secondMessage.getElapsedTime(), 18);
		assertEquals(secondMessage.getTotalNumOfQuery(), 65);
		
		if(shouldCallClear) {
			verify(queryCountHolder).clear();
		}
		else {
			verify(queryCountHolder, never()).clear();
		}
	}
}
