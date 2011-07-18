package net.ttddyy.dsproxy.proxy;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Proxy InvocationHandler for {@link java.sql.PreparedStatement}.
 *
 * @author Tadaya Tsuyukubo
 * @author Niall Thomson
 */
public class PreparedStatementInvocationHandler extends AbstractStatementInvocationHandler<PreparedStatement> {
	
	private static final Set<String> PARAMETER_METHODS = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList("setArray", "setAsciiStream", "setBigDecimal",
                    "setBinaryStream", "setBlob", "setBoolean", "setByte",
                    "setBytes", "setCharacterStream", "setClob", "setDate",
                    "setDouble", "setFloat", "setInt", "setLong",
                    "setNull", "setObject", "setRef", "setShort",
                    "setString", "setTime", "setTimestamp", "setUnicodeStream", "setURL",
                    "clearParameters"
            ))
    );

    private static Set<String> METHODS_TO_OPERATE_PARAMETER;
    private static Set<String> METHODS_TO_INTERCEPT;

    private String query;
    private SortedMap<Integer, Object> queryParams = new TreeMap<Integer, Object>(); // sorted by key

    public PreparedStatementInvocationHandler(PreparedStatement ps, String query) {
        super(ps);
        this.query = query;
    }

    public PreparedStatementInvocationHandler(PreparedStatement ps, String query, QueryExecutionListener listener) {
        super(ps, listener);
        this.query = query;
    }

    public PreparedStatementInvocationHandler(
            PreparedStatement ps, String query, QueryExecutionListener listener, String dataSourceName) {
        super(ps, listener, dataSourceName);
        this.query = query;
    }

	@Override
	public Set<String> getMethodsToIntercept() {
		return getMethodsToInterceptList();
	}

	@Override
	public Set<String> getMethodsToOperateParameter() {
		return getMethodsToOperateList();
	}

	private static Set<String> getMethodsToOperateList() {
		if(METHODS_TO_OPERATE_PARAMETER == null) {
			METHODS_TO_OPERATE_PARAMETER = new HashSet<String>();
			METHODS_TO_OPERATE_PARAMETER.addAll(AbstractStatementInvocationHandler.getBatchParamMethods());
			METHODS_TO_OPERATE_PARAMETER.addAll(PARAMETER_METHODS);
		}
		
		return METHODS_TO_OPERATE_PARAMETER;
	}

	@Override
	public String getQuery(Object[] args) {
		return query;
	}

	@Override
	public List<Object> getQueryArgs() {
		return new ArrayList<Object>(queryParams.values());
	}

	private static Set<String> getMethodsToInterceptList() {
		if(METHODS_TO_INTERCEPT == null) {
			METHODS_TO_INTERCEPT = new HashSet<String>();
			METHODS_TO_INTERCEPT.addAll(StatementInvocationHandler.getMethodsToInterceptList());
			METHODS_TO_INTERCEPT.addAll(PARAMETER_METHODS);
		}
		
		return METHODS_TO_INTERCEPT;
	}
	
	@Override
	protected void handleMethodsToOperateParameter(Method method,
			Object[] args) throws Throwable {
		String methodName = method.getName();

		if (PARAMETER_METHODS.contains(methodName)) {
			 // operation to set or clear parameters
            if ("clearParameters".equals(methodName)) {
                queryParams.clear();
            } else {
                final Integer paramKey = (Integer)args[0]; // key can be int or string
                final Object paramValue = args[1];
                queryParams.put(paramKey, paramValue);
            }
		}
		else {
			super.handleMethodsToOperateParameter(method, args);
		}
	}
}
