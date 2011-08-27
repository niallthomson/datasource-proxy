package net.ttddyy.dsproxy;

import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class QueryCountHolderTest {
	QueryCountHolder queryCountHolder;
	
	@BeforeTest
	public void beforeTest() {
		queryCountHolder = new QueryCountHolder();
		
		QueryCount firstQueryCount = new QueryCount(1, 2, 3, 4, 5, 6, 7, new Long(8));
		queryCountHolder.put("first", firstQueryCount);
		
		QueryCount secondQueryCount = new QueryCount(11, 12, 13, 14, 15, 16, 17, new Long(18));
		queryCountHolder.put("second", secondQueryCount);
	}

	@Test
	public void getDataSourceNames() {
		Set<String> names = queryCountHolder.getDataSourceNames();
		
		assertEquals(names.size(), 2);
		assertTrue(names.contains("first"));
		assertTrue(names.contains("second"));
	}

	@Test
	public void getDataSourceNamesAsList() {
		List<String> names = queryCountHolder.getDataSourceNamesAsList();
		
		assertEquals(names.size(), 2);
		assertTrue(names.contains("first"));
		assertTrue(names.contains("second"));
	}

	@Test
	public void getGrandTotal() {
		QueryCount total = queryCountHolder.getGrandTotal();
		
		assertEquals(total.getSelect(), 12);
		assertEquals(total.getInsert(), 14);
		assertEquals(total.getUpdate(), 16);
		assertEquals(total.getDelete(), 18);
		assertEquals(total.getOther(), 20);
		assertEquals(total.getCall(), 22);
		assertEquals(total.getFailure(), 24);
		assertEquals(total.getElapsedTime(), 26);
		assertEquals(total.getTotalNumOfQuery(), 80);
	}
}
