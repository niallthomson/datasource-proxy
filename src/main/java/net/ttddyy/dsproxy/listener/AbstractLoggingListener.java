package net.ttddyy.dsproxy.listener;

import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.support.logging.ILogger;

/**
 * Base class for logging listeners.
 * 
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public abstract class AbstractLoggingListener extends AbstractQueryExecutionListener {
	private ILogger logger;

	public void setMessageFormat(String format) {
		this.logger.setFormat(format);
	}

	public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		StringBuilder sb = new StringBuilder();

		for (QueryInfo queryInfo : queryInfoList) {
			sb.append("{");
			final String query = queryInfo.getQuery();
			final List<?> args = queryInfo.getQueryArgs();

			sb.append("[");
			sb.append(query);
			sb.append("][");

			for (Object arg : args) {
				sb.append(arg);
				sb.append(',');
			}

			chopIfEndWith(sb, ',');

			sb.append("]");
			sb.append("} ");
		}

		LogMessage message = new LogMessage(execInfo.getDataSourceName(),
				execInfo.getResult(), execInfo.getElapsedTime(), sb.toString(),
				queryInfoList.size());
		
		logger.log(message);
	}

	private void chopIfEndWith(StringBuilder sb, char c) {
		final int lastCharIndex = sb.length() - 1;
		if (sb.charAt(lastCharIndex) == c) {
			sb.deleteCharAt(lastCharIndex);
		}
	}
}

/**
 * Bean representing a log message.
 * 
 * @author Niall Thomson
 */
class LogMessage {
	private String dataSourceName;
	private Object result;
	private long elapsedTime;
	private String queryString;
	private int numQueries;

	public LogMessage(String dataSourceName, Object result, long elapsedTime,
			String queryString, int numQueries) {
		this.dataSourceName = dataSourceName;
		this.result = result;
		this.elapsedTime = elapsedTime;
		this.queryString = queryString;
		this.numQueries = numQueries;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public int getNumQueries() {
		return numQueries;
	}

	public void setNumQueries(int numQueries) {
		this.numQueries = numQueries;
	}
}