package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

/**
 * Base class for Statement InvocationHandlers
 * 
 * @author Niall Thomson
 *
 * @param <E> Statement type that this handler deals with
 */
public abstract class AbstractStatementInvocationHandler<E extends Statement>
		implements InvocationHandler {
	private E wrappedStatement;
	private QueryExecutionListener listener;
	private String dataSourceName;
	private List<BatchQueryHolder> batchQueries = new ArrayList<BatchQueryHolder>();

	private static final Set<String> BATCH_PARAM_METHODS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("addBatch",
					"clearBatch")));

	private static final Set<String> EXEC_METHODS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("executeBatch",
					"executeQuery", "executeUpdate", "execute")));

	private static final Set<String> JDBC4_METHODS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("unwrap",
					"isWrapperFor")));

	private static final Set<String> GET_CONNECTION_METHOD = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("getConnection")));

	protected static final Set<String> METHODS_TO_INTERCEPT = Collections
			.unmodifiableSet(new HashSet<String>() {
				private static final long serialVersionUID = 1L;

				{
					addAll(BATCH_PARAM_METHODS);
					addAll(EXEC_METHODS);
					addAll(JDBC4_METHODS);
					addAll(GET_CONNECTION_METHOD);
					add("getDataSourceName");
					add("toString");
					add("getTarget");
				}
			});

	public AbstractStatementInvocationHandler(E stmt) {
		this.wrappedStatement = stmt;
	}

	public AbstractStatementInvocationHandler(E stmt,
			QueryExecutionListener listener) {
		this.wrappedStatement = stmt;
		this.listener = listener;
	}

	public AbstractStatementInvocationHandler(E stmt,
			QueryExecutionListener listener, String dataSourceName) {
		this.wrappedStatement = stmt;
		this.listener = listener;
		this.dataSourceName = dataSourceName;
	}

	public static Set<String> getBatchParamMethods() {
		return BATCH_PARAM_METHODS;
	}

	public Set<String> getExecMethods() {
		return EXEC_METHODS;
	}

	public Set<String> getJDBC4Methods() {
		return JDBC4_METHODS;
	}

	public Set<String> getConnectionMethod() {
		return GET_CONNECTION_METHOD;
	}

	public abstract Set<String> getMethodsToIntercept();

	public abstract Set<String> getMethodsToOperateParameter();

	protected E getWrappedStatement() {
		return this.wrappedStatement;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		final String methodName = method.getName();

		if (!getMethodsToIntercept().contains(methodName)) {
			return MethodUtils.proceedExecution(method, wrappedStatement, args);
		}

		// special treat for toString method
		if ("toString".equals(methodName)) {
			StringBuilder sb = new StringBuilder("StatementInvocationHandler [");
			sb.append(wrappedStatement.toString());
			sb.append("]");
			return sb.toString(); // differentiate toString message.
		} else if ("getDataSourceName".equals(methodName)) {
			return dataSourceName;
		} else if ("getTarget".equals(methodName)) {
			// ProxyJdbcObject interface has method to return original object.
			return wrappedStatement;
		}

		if (getJDBC4Methods().contains(methodName)) {
			final Class<?> clazz = (Class<?>) args[0];
			if ("unwrap".equals(methodName)) {
				return wrappedStatement.unwrap(clazz);
			} else if ("isWrapperFor".equals(methodName)) {
				return wrappedStatement.isWrapperFor(clazz);
			}
		}

		if (getConnectionMethod().contains(methodName)) {
			final Connection conn = (Connection) MethodUtils.proceedExecution(
					method, wrappedStatement, args);
			return JdbcProxyFactory.createConnection(conn, listener,
					dataSourceName);
		}

		if (getMethodsToOperateParameter().contains(methodName)) {
			handleMethodsToOperateParameter(method, args);
			
            return MethodUtils.proceedExecution(method, wrappedStatement, args);
		}

		final List<QueryInfo> queries = new ArrayList<QueryInfo>();

		if ("executeBatch".equals(methodName)) {
			for (BatchQueryHolder batchQuery : batchQueries) {
				queries.add(new QueryInfo(batchQuery.getQuery(), batchQuery
						.getArgs()));
			}
		} else if ("executeQuery".equals(methodName)
				|| "executeUpdate".equals(methodName)
				|| "execute".equals(methodName)) {
			String query = getQuery(args);

			if (query != null) {
				queries.add(new QueryInfo(query, getQueryArgs()));
			}
		}

		ExecutionInfo execInfo = new ExecutionInfo(dataSourceName, method, args);

		listener.beforeQuery(new ExecutionInfo(dataSourceName, method, args), queries);

		// Invoke method on original Statement.
		try {
			final long beforeTime = System.currentTimeMillis();

			Object retVal = listener.aroundQuery(execInfo, queries,
					wrappedStatement, method, args);

			final long afterTime = System.currentTimeMillis();
			execInfo.setResult(retVal);
			execInfo.setElapsedTime(afterTime - beforeTime);

			return retVal;
		} catch (InvocationTargetException ex) {
			execInfo.setThrowable(ex.getTargetException());
			throw ex.getTargetException();
		} finally {
			listener.afterQuery(execInfo, queries);
		}

	}

	protected void handleMethodsToOperateParameter(Method method,
			Object[] args) throws Throwable {
		String methodName = method.getName();

		if (getBatchParamMethods().contains(methodName)) {

			// Batch parameter operation
			if ("addBatch".equals(methodName)) {
				String query = getQuery(args);

				if (query != null) {
					BatchQueryHolder queryHolder = new BatchQueryHolder();
					queryHolder.setQuery(getQuery(args));
					queryHolder.setArgs(getQueryArgs());
					getBatchQueries().add(queryHolder);
				}
			} else {
				batchQueries.clear();
			}
		}
	}

	public abstract String getQuery(Object[] args);

	public abstract List<Object> getQueryArgs();

	protected List<BatchQueryHolder> getBatchQueries() {
		return batchQueries;
	}
}

class ResultWrapper {
	Object result;
}