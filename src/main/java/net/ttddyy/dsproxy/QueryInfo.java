package net.ttddyy.dsproxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tadaya Tsuyukubo
 */
public class QueryInfo {
    private String query;
    private List<?> queryArgs = new ArrayList<Object>();

    public QueryInfo() {
    }

    public QueryInfo(String query, List queryArgs) {
        this.query = query;
        if (queryArgs != null) {
            this.queryArgs.addAll(queryArgs);
        }
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<?> getQueryArgs() {
        return this.queryArgs;
    }

    public void setQueryArgs(List<?> queryArgs) {
        this.queryArgs = queryArgs;
    }

    public String buildQueryString() {
    	StringBuilder sb = new StringBuilder();
		
		int currentIndex = this.query.indexOf("?");
		int lastIndex = 0;
		
		int argCounter = 0;
		
		while(currentIndex > 0) {
			sb.append(this.query.substring(lastIndex, currentIndex));
			
			sb.append(this.queryArgs.get(argCounter));
			
			lastIndex = currentIndex;
			
			currentIndex = this.query.indexOf("?", lastIndex + 1);
		}
		
		return sb.toString();
    }
    
    public boolean equals(Object object) {	
    	if(object instanceof QueryInfo) {
    		QueryInfo other = (QueryInfo)object;
    		
    		return this.query.equals(other.query) && this.queryArgs.equals(other.queryArgs);
    	}
    	
    	return false;
    }
}
