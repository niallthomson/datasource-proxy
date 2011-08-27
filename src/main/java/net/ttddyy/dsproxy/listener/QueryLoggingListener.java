package net.ttddyy.dsproxy.listener;

import java.util.List;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.support.logging.ILogger;

/**
 * Listener which logs all queries.
 * 
 * @author Niall Thomson
 * @author Tadaya Tsuyukubo
 */
public class QueryLoggingListener extends AbstractQueryExecutionListener {
	/**
	 * The logger instance to write to
	 */
	private ILogger logger;
	
	/**
	 * The format string to give the logger
	 */
	private String format;
	
	/**
	 * The default format string
	 */
	private static String DEFAULT_FORMAT = "DataSource:{dataSourceName} ElapsedTime:{elapsedTime} Query:{queryString}";
	
	public QueryLoggingListener() {
		this.setFormat(DEFAULT_FORMAT);
	}
	
	public QueryLoggingListener(ILogger logger) {
		this();
		
		this.setLogger(logger);
	}

	public void setFormat(String format) {
		this.format = format;
		
		if(this.logger != null) {
			this.logger.setFormat(format);
		}
	}
	
	public void setLogger(ILogger logger) {
		this.logger = logger;
		
		this.logger.setFormat(format);
	}

	public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		String queryString = buildQueryListString(queryInfoList);

		LogMessage message = new LogMessage(execInfo.getDataSourceName(),
				execInfo.getResult(), execInfo.getElapsedTime(), queryString,
				queryInfoList.size());
		
		logger.log(message);
	}
	
	/**
	 * Builds a single query string from a list of QueryInfo objects.
	 * 
	 * @param 	queryInfoList	the list of queries to generate a string for
	 * @return					the generated query string
	 */
	private String buildQueryListString(List<QueryInfo> queryInfoList) {
		StringBuilder sb = new StringBuilder();

		for (QueryInfo queryInfo : queryInfoList) {
			sb.append("{");

			sb.append(queryInfo.buildQueryString());
			
			sb.append("} ");
		}
		
		return sb.toString();
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
	
	public boolean equals(Object object) {
		if(!(object instanceof LogMessage)) {
			return false;
		}
		
		LogMessage other = (LogMessage)object;
		
		if(!other.getDataSourceName().equals(this.getDataSourceName())) {
			return false;
		}
		
		if(!other.getResult().equals(this.getResult())) {
			return false;
		}
		
		if(other.getElapsedTime() != this.getElapsedTime()) {
			return false;
		}
		
		if(!other.getQueryString().equals(this.getQueryString())) {
			return false;
		}
		
		if(other.getNumQueries() != this.getNumQueries()) {
			return false;
		}
		
		return true;
	}
}