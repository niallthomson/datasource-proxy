package net.ttddyy.dsproxy.support.logging;

import java.util.Collections;
import java.util.List;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

public abstract class AbstractQueryCountLogger {
	private ILogger logger;
	private boolean clearQueryCounter;
	
	private QueryCountHolder queryCountHolder;
	
	private static String DEFAULT_FORMAT = "DataSource:{dsName} ElapsedTime:{elapsedTime} Call:{call} Query:{totalNumOfQuery} (Select:{select} Insert:{insert} Update:{update} Delete:{delete} Other:{other})";
	
	public AbstractQueryCountLogger() {
		this.setFormat(DEFAULT_FORMAT);
	}

	protected void logQueryCount() {
		final List<String> dsNames = this.getQueryCountHolder().getDataSourceNamesAsList();
		Collections.sort(dsNames);

		for (String dsName : dsNames) {
			final QueryCount counter = this.getQueryCountHolder().get(dsName);

			this.writeMessage(new QueryCountLogMessage(dsName, counter));
		}

		if (this.clearQueryCounter) {
			this.getQueryCountHolder().clear();
		}
	}
	
	protected void writeMessage(QueryCountLogMessage message) {
		this.logger.log(message);
	}

	public void setClearQueryCounter(boolean clearQueryCounter) {
		this.clearQueryCounter = clearQueryCounter;
	}
	
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}
	
	public void setLogLevel(String logLevel) {
		if(this.logger != null) {
			this.logger.setLogLevel(logLevel);
		}
	}
	
	public void setFormat(String format) {
		this.logger.setFormat(format);
	}
	
	protected QueryCountHolder getQueryCountHolder() {
		if(this.queryCountHolder == null) {
			this.queryCountHolder = QueryCountHolder.getDefaultInstance();
		}
		
		return this.queryCountHolder;
	}
	
	protected void setQueryCountHolder(QueryCountHolder queryCountHolder) {
		this.queryCountHolder = queryCountHolder;
	}
}

class QueryCountLogMessage {
	private String dsName;
	private QueryCount queryCount;

	public QueryCountLogMessage(String dsName, QueryCount queryCount) {
		this.dsName = dsName;
		this.queryCount = queryCount;
	}

	public String getDsName() {
		return this.dsName;
	}
	
	public long getElapsedTime() {
		return this.queryCount.getElapsedTime();
	}
	
	public int getCall(){
		return this.queryCount.getCall();
	}
	
	public int getTotalNumOfQuery() {
		return this.queryCount.getTotalNumOfQuery();
	}
	
	public int getSelect() {
		return this.queryCount.getSelect();
	}
	
	public int getInsert() {
		return this.queryCount.getInsert();
	}
	
	public int getUpdate() {
		return this.queryCount.getUpdate();
	}

	public int getDelete() {
		return this.queryCount.getDelete();
	}

	public int getOther() {
		return this.queryCount.getOther();
	}
	
	public int getFailure() {
		return this.queryCount.getFailure();
	}
}