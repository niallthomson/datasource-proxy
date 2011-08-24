package net.ttddyy.dsproxy.support.logging;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

import org.mockito.ArgumentCaptor;
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
	public void setupTest() {
		firstQueryCount = new QueryCount();
		firstQueryCount.setSelect(1);
		
		secondQueryCount = new QueryCount();
		secondQueryCount.setSelect(2);
		
		dsNames = new ArrayList<String>();
		dsNames.add("first");
		dsNames.add("second");
		
		queryCountLogger = mock(AbstractQueryCountLogger.class);
		doCallRealMethod().when(queryCountLogger).setQueryCountHolder(any(QueryCountHolder.class));
		doCallRealMethod().when(queryCountLogger).setLogger(any(ILogger.class));
		doCallRealMethod().when(queryCountLogger).writeMessage(any(QueryCountLogMessage.class));
		doCallRealMethod().when(queryCountLogger).logQueryCount();
		doCallRealMethod().when(queryCountLogger).setClearQueryCounter(anyBoolean());
	}

	@BeforeMethod
	public void completeSetup() {
		queryCountHolder = mock(QueryCountHolder.class);
		when(queryCountHolder.getDataSourceNamesAsList()).thenReturn(dsNames);
		when(queryCountHolder.get("first")).thenReturn(firstQueryCount);
		when(queryCountHolder.get("second")).thenReturn(secondQueryCount);
		
		logger = mock(ILogger.class);
		
		queryCountLogger.setLogger(logger);
		queryCountLogger.setQueryCountHolder(queryCountHolder);
	}
	
	@Test
	public void testLogQueryCountNoClear() {
		queryCountLogger.setClearQueryCounter(false);
		queryCountLogger.logQueryCount();
		
		verifyResult(logger, false);
	}
	
	@Test
	public void testLogQueryCountWithClear() {
		queryCountLogger.setClearQueryCounter(true);
		queryCountLogger.logQueryCount();
		
		verifyResult(logger, true);
	}
	
	private void verifyResult(ILogger logger, boolean shouldCallClear) {
		ArgumentCaptor<QueryCountLogMessage> logMessageCaptor = ArgumentCaptor.forClass(QueryCountLogMessage.class);
		
		verify(logger, times(2)).log(logMessageCaptor.capture());
		
		List<QueryCountLogMessage> messages = logMessageCaptor.getAllValues();
		assertEquals(messages.get(0).getSelect(), 1);
		assertEquals(messages.get(1).getSelect(), 2);
		
		if(shouldCallClear) {
			verify(queryCountHolder).clear();
		}
		else {
			verify(queryCountHolder, never()).clear();
		}
	}
}
