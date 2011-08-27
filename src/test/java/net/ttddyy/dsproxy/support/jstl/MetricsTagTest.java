package net.ttddyy.dsproxy.support.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;

public class MetricsTagTest {
	MetricsTag metricsTag;

	JspContext jspContext;
	JspWriter jspWriter;

	QueryCountHolder queryCountHolder;
	QueryCount queryCount;

	String dsName;
	String wrongDsName;

	@BeforeMethod
	public void beforeMethod() {
		jspWriter = mock(JspWriter.class);
		when(jspContext.getOut()).thenReturn(jspWriter);
	}

	@BeforeTest
	public void beforeTest() {
		dsName = "datasource";
		queryCount = new QueryCount(1, 2, 3, 4, 5, 6, 7, new Long(8));

		queryCountHolder = mock(QueryCountHolder.class);
		when(queryCountHolder.get(dsName)).thenReturn(queryCount);
		when(queryCountHolder.get(wrongDsName)).thenReturn(null);

		jspContext = mock(JspContext.class);

		metricsTag = new MetricsTag();
		metricsTag.setJspContext(jspContext);
		metricsTag.setQueryCountHolder(queryCountHolder);
	}

	@Test(dataProvider = "metricsProvider")
	public void doTagSelect(String metric, String result) throws JspException,
			IOException {
		metricsTag.setMetric(metric);
		metricsTag.setDataSource(dsName);

		metricsTag.doTag();

		verify(jspWriter).print(result);
	}

	@DataProvider(name = "metricsProvider")
	public Object[][] metricsProvider() {
		return new Object[][] { { "select", "1" }, { "insert", "2" },
				{ "update", "3" }, { "delete", "4" }, { "other", "5" },
				{ "call", "6" }, { "failure", "7" }, { "elapsedTime", "8" }, };
	}
	
	@Test
	public void doTagSelectNullCount() throws JspException,
			IOException {
		metricsTag.setMetric("select");
		metricsTag.setDataSource(wrongDsName);

		metricsTag.doTag();

		verify(jspWriter, never()).print(anyString());
	}
	
	@Test
	public void doTagSelectBadMetric() throws JspException,
			IOException {
		metricsTag.setMetric("bad");
		metricsTag.setDataSource(dsName);

		metricsTag.doTag();

		verify(jspWriter, never()).print(anyString());
	}
}
