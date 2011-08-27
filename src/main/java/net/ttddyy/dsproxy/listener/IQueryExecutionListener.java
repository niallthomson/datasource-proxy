package net.ttddyy.dsproxy.listener;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Listener interface. Inject the implementation to proxy handler interceptors.
 *
 * @author Tadaya Tsuyukubo
 * @see ChainListener
 * @see net.ttddyy.dsproxy.proxy.dynamic.ConnectionInvocationHandler
 * @see net.ttddyy.dsproxy.proxy.dynamic.PreparedStatementInvocationHandler
 * @see net.ttddyy.dsproxy.proxy.dynamic.StatementInvocationHandler
 */
public interface IQueryExecutionListener {

    void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList);
    
    Object aroundQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, Object target, Method method, Object[] args)  throws Throwable;

    void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList);
}
