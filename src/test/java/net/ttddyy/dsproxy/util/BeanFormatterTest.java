package net.ttddyy.dsproxy.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BeanFormatterTest {
	@Test
    public void testBeanFormatter() throws Throwable {
        TestObject testObject = new TestObject(3, "test", true);
        
        BeanFormatter formatter = new BeanFormatter("{integerField} {stringField} {booleanField}");
        
        String result = formatter.format(testObject);
        
        assertEquals(result, "3 test true");
    }
	
	@Test
    public void testBeanFormatterNoProperties() throws Throwable {
        TestObject testObject = new TestObject(3, "test", true);
        
        BeanFormatter formatter = new BeanFormatter("integerField stringField booleanField");
        
        String result = formatter.format(testObject);
        
        assertEquals(result, "integerField stringField booleanField");
    }
	
	@Test(expectedExceptions={Exception.class})
    public void testBeanFormatterInvalidProperty() throws Throwable {
        TestObject testObject = new TestObject(3, "test", true);
        
        BeanFormatter formatter = new BeanFormatter("{integerField1}");
        
        formatter.format(testObject);
    }
	
	@Test(expectedExceptions={IllegalArgumentException.class})
    public void testBeanFormatterNullFormatString() throws Throwable {
        new BeanFormatter(null);
    }
}

class TestObject {
	private int integerField;
	private String stringField;
	private boolean booleanField;
	
	public TestObject(int integerField, String stringField, boolean booleanField) {
		super();
		this.integerField = integerField;
		this.stringField = stringField;
		this.booleanField = booleanField;
	}
	public int getIntegerField() {
		return integerField;
	}
	public void setIntegerField(int integerField) {
		this.integerField = integerField;
	}
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	public boolean isBooleanField() {
		return booleanField;
	}
	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}
	
	
}