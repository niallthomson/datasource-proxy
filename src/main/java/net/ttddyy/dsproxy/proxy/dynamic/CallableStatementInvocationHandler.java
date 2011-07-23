package net.ttddyy.dsproxy.proxy.dynamic;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Proxy InvocationHandler for {@link java.sql.CallableStatement}.
 *
 * @author Tadaya Tsuyukubo
 * @author Niall Thomson
 */
public class CallableStatementInvocationHandler extends AbstractStatementInvocationHandler<CallableStatement> {
	
	private String query;
	private SortedMap<Object, Object> queryParams = new TreeMap<Object, Object>();

	public CallableStatementInvocationHandler(CallableStatement stmt, String query,
			QueryExecutionListener listener, String dataSourceName) {
		super(stmt, listener, dataSourceName);
		
		this.query = query;
	}

	@Override
	public Set<String> getMethodsToIntercept() {
		return PreparedStatementInvocationHandler.getMethodsToInterceptList();
	}

	@Override
	public Set<String> getMethodsToOperateParameter() {
		return PreparedStatementInvocationHandler.getMethodsToOperateList();
	}

	@Override
	public String getQuery(Object[] args) {
		return this.query;
	}

	@Override
	public List<Object> getQueryArgs() {
		return new ArrayList<Object>(queryParams.values());
	}
	
	@Override
	protected void handleMethodsToOperateParameter(Method method,
			Object[] args) throws Throwable {
		String methodName = method.getName();

		if (getParameterMethods().contains(methodName)) {
			 // operation to set or clear parameters
            if ("clearParameters".equals(methodName)) {
                queryParams.clear();
            } else {
                final Object paramKey = args[0]; // key can be int or string
                final Object paramValue = args[1];
                queryParams.put(paramKey, paramValue);
            }
		}
		else {
			super.handleMethodsToOperateParameter(method, args);
		}
	}
	
	protected Set<String> getParameterMethods() {
		return PreparedStatementInvocationHandler.getParameterMethods();
	}
}