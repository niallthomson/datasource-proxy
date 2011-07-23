package net.ttddyy.dsproxy.listener;

import java.lang.reflect.Method;
import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

/**
 * A base class for QueryExecutionListeners, since we don't want them to all have 
 * to implements the aroundQuery() method if they don't need it.
 * 
 * @author Niall Thomson
 */
public abstract class AbstractQueryExecutionListener implements QueryExecutionListener {
	public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		
	}
    
    public Object aroundQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, Object target, Method method, Object[] args) throws Throwable {
    	return method.invoke(target, args);
    }

    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {

    }
}
