package net.ttddyy.dsproxy.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AbstractListenerTest {
	ExecutionInfo execInfo;
	List<QueryInfo>queryInfoList;
	
	TestClass target;
	
	Object[] args;
	
	@BeforeTest
	public void setupTests() {
		execInfo = new ExecutionInfo();
		queryInfoList = new ArrayList<QueryInfo>();
		
		args = null;
	}
	
	@BeforeMethod
	public void setupTest() {
		target = spy(new TestClass());
	}
	
	@Test
	public void testAroundQuery() throws Throwable {
		Method method = TestClass.class.getMethod("testMethod");

		AbstractQueryExecutionListener listener = mock(AbstractQueryExecutionListener.class);
		doCallRealMethod().when(listener).aroundQuery(execInfo, queryInfoList, target, method, args);
		
		listener.aroundQuery(execInfo, queryInfoList, target, method, args);
		
		verify(target).testMethod();
	}
	
	@Test(expectedExceptions={Exception.class})
	public void testAroundQueryThrowsException() throws Throwable {
		Method method = TestClass.class.getMethod("testMethodException");

		AbstractQueryExecutionListener listener = mock(AbstractQueryExecutionListener.class);
		doCallRealMethod().when(listener).aroundQuery(execInfo, queryInfoList, target, method, args);
		
		listener.aroundQuery(execInfo, queryInfoList, target, method, args);
	}
}

class TestClass {
	public void testMethod() {
		// Nothing
	}
	
	public void testMethodException() throws Exception {
		throw new Exception("Test");
	}
}