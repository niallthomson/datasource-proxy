package net.ttddyy.dsproxy.support.logging;

import net.ttddyy.dsproxy.util.BeanFormatter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyObject;

public class AbstractLoggerTest {
	Object object;
	AbstractLogger logger;
	
	@BeforeMethod
	public void setupTest() {
		createLogger();
	}
	
	@Test
	public void testAbstractLoggerLog() throws Exception {
		BeanFormatter formatter = mock(BeanFormatter.class);
		when(formatter.format(object)).thenReturn("test");
		
		AbstractLogger logger = mock(AbstractLogger.class);
		doCallRealMethod().when(logger).log(object);
		doCallRealMethod().when(logger).setFormatter(formatter);
		
		logger.setFormatter(formatter);

		logger.log(object);
		
		verify(logger).writeLog("test");
	}
	
	@Test
	public void testAbstractLoggerLogFormatException() throws Exception {
		BeanFormatter formatter = mock(BeanFormatter.class);
		when(formatter.format(object)).thenThrow(new Exception());
		
		AbstractLogger logger = mock(AbstractLogger.class);
		doCallRealMethod().when(logger).log(object);
		doCallRealMethod().when(logger).setFormatter(formatter);
		
		logger.setFormatter(formatter);

		logger.log(object);
		
		verify(logger).writeLog("Failed to format log message");
	}
	
	@Test
	public void testAbstractLoggerSetFormat() throws Exception {
		AbstractLogger logger = mock(AbstractLogger.class);
		doCallRealMethod().when(logger).setFormat("format");
		
		logger.setFormat("format");

		verify(logger).setFormatter(any(BeanFormatter.class));
	}
	
	private void createLogger() {
		logger = mock(AbstractLogger.class);
		doCallRealMethod().when(logger).log(anyObject());
		doCallRealMethod().when(logger).setFormatter(any(BeanFormatter.class));
	}
}
