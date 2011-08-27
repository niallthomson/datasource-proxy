package net.ttddyy.dsproxy.listener;

import java.util.ArrayList;
import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChainListenerTest {
	IQueryExecutionListener listener1;
	IQueryExecutionListener listener2;
	
	ChainListener chainListener;
	
	ExecutionInfo execInfo;
	List<QueryInfo> queryInfoList;
	
	@BeforeTest
	public void setupTests() {
		execInfo = new ExecutionInfo();
		queryInfoList = new ArrayList<QueryInfo>();
	}
	
	@BeforeMethod 
	public void setupTest() {
		listener1 = mock(IQueryExecutionListener.class);
		listener2 = mock(IQueryExecutionListener.class);
		
		chainListener = new ChainListener();
		chainListener.addListener(listener1);
		chainListener.addListener(listener2);
	}
	
	@Test
	public void testChainListenerBeforeQuery() {	
		chainListener.beforeQuery(execInfo, queryInfoList);
		
		verify(listener1).beforeQuery(execInfo, queryInfoList);
		verify(listener2).beforeQuery(execInfo, queryInfoList);
	}
	
	@Test
	public void testChainListenerAfterQuery() {	
		chainListener.afterQuery(execInfo, queryInfoList);
		
		verify(listener1).afterQuery(execInfo, queryInfoList);
		verify(listener2).afterQuery(execInfo, queryInfoList);
	}
}
