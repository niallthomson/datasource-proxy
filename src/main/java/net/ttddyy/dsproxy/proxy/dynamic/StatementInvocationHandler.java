package net.ttddyy.dsproxy.proxy.dynamic;

import net.ttddyy.dsproxy.listener.IQueryExecutionListener;
import net.ttddyy.dsproxy.proxy.ObjectArrayUtils;

import java.sql.Statement;
import java.util.List;
import java.util.Set;

/**
 * Proxy InvocationHandler for {@link java.sql.Statement}.
 *
 * @author Tadaya Tsuyukubo
 */
public class StatementInvocationHandler extends AbstractStatementInvocationHandler<Statement> {
    public StatementInvocationHandler(
            Statement stmt, IQueryExecutionListener listener, String dataSourceName) {
        super(stmt, listener, dataSourceName);
    }

	@Override
	public Set<String> getMethodsToIntercept() {
		return getMethodsToInterceptList();
	}

	@Override
	public Set<String> getMethodsToOperateParameter() {
		return getBatchParamMethods();
	}

	@Override
	public String getQuery(Object[] args) {
		if(ObjectArrayUtils.isFirstArgString(args)) {
			return (String)args[0];
		}
		
		return null;
	}

	@Override
	public List<Object> getQueryArgs() {
		return null;
	}
	
	public static Set<String> getMethodsToInterceptList() {
		return AbstractStatementInvocationHandler.METHODS_TO_INTERCEPT;
	}
}
