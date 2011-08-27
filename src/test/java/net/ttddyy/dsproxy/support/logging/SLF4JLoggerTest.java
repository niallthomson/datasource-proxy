package net.ttddyy.dsproxy.support.logging;

import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SLF4JLoggerTest {

	SLF4JLogger commonsLogger;
	Logger logger;

	@BeforeMethod
	public void beforeMethod() {
		logger = mock(Logger.class);
		
		StaticLoggerBinder.getSingleton().setLogger(logger);
		
		commonsLogger = new SLF4JLogger();
	}

	@Test
	public void writeLogDebug() {
		commonsLogger.setLogLevel("DEBUG");

		commonsLogger.writeLog("message");

		verify(logger).debug("message");
	}

	@Test
	public void writeLogError() {
		commonsLogger.setLogLevel("ERROR");

		commonsLogger.writeLog("message");

		verify(logger).error("message");
	}

	@Test
	public void writeLogInfo() {
		commonsLogger.setLogLevel("INFO");

		commonsLogger.writeLog("message");

		verify(logger).info("message");
	}
	
	@Test
	public void writeLogTrace() {
		commonsLogger.setLogLevel("TRAC");

		commonsLogger.writeLog("message");

		verify(logger).trace("message");
	}
	
	@Test
	public void writeLogWarn() {
		commonsLogger.setLogLevel("WARN");

		commonsLogger.writeLog("message");

		verify(logger).warn("message");
	}
}
