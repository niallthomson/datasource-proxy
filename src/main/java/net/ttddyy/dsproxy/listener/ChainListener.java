package net.ttddyy.dsproxy.listener;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Execute chain of listeners.
 *
 * @author Tadaya Tsuyukubo
 */
public class ChainListener extends AbstractQueryExecutionListener {
    private List<IQueryExecutionListener> listeners = new ArrayList<IQueryExecutionListener>();

    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        for (IQueryExecutionListener listener : listeners) {
            listener.beforeQuery(execInfo, queryInfoList);
        }
    }

    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        for (IQueryExecutionListener listener : listeners) {
            listener.afterQuery(execInfo, queryInfoList);
        }
    }

    public void addListener(IQueryExecutionListener listner) {
        this.listeners.add(listner);
    }

    public List<IQueryExecutionListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<IQueryExecutionListener> listeners) {
        this.listeners = listeners;
    }
}
