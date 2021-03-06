package net.ttddyy.dsproxy.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting a string using fields from a bean.
 * 
 * The format string should specify properties from the bean using curly
 * brackets:
 * 
 * "Value of some field = {fieldName}"
 * 
 * Got this from
 * http://stackoverflow.com/questions/6734115/how-do-i-format-a-string
 * -with-properties-from-a-bean/6734857#6734857
 * 
 * @author Niall Thomson
 */
public class BeanFormatter {

	private Matcher matcher;
	private static final Pattern pattern = Pattern.compile("\\{(.+?)\\}");

	public BeanFormatter(String formatString) {
		if(formatString == null) {
			throw new IllegalArgumentException("Format string cannot be null");
		}
		
		this.setFormat(formatString);
	}

	public String format(Object bean) throws Exception {
		StringBuffer buffer = new StringBuffer();

		try {
			matcher.reset();
			
			while (matcher.find()) {
				String token = matcher.group(1);
				String value = getProperty(bean, token);
				matcher.appendReplacement(buffer, value);
			}
			
			matcher.appendTail(buffer);
		} catch (Exception ex) {
			throw new Exception("Error formatting bean " + bean.getClass()
					+ " with format " + matcher.pattern().toString(), ex);
		}
		
		return buffer.toString();
	}

	private String getProperty(Object bean, String token)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = bean.getClass().getDeclaredField(token);
		field.setAccessible(true);
		
		return String.valueOf(field.get(bean));
	}
	
	public void setFormat(String format) {
		this.matcher = pattern.matcher(format);
	}
}
