package net.ttddyy.dsproxy.support.logging;

import org.apache.commons.logging.Log;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommonsLoggerTest {

	CommonsLogger commonsLogger;
	Log log;

	@BeforeTest
	public void beforeTest() {
		commonsLogger = new CommonsLogger();
	}

	@BeforeMethod
	public void beforeMethod() {
		log = mock(Log.class);

		commonsLogger.setLog(log);
	}

	@Test
	public void writeLogDebug() {
		commonsLogger.setLogLevel("DEBUG");

		commonsLogger.writeLog("message");

		verify(log).debug("message");
	}

	@Test
	public void writeLogError() {
		commonsLogger.setLogLevel("ERROR");

		commonsLogger.writeLog("message");

		verify(log).error("message");
	}

	@Test
	public void writeLogFatal() {
		commonsLogger.setLogLevel("FATAL");

		commonsLogger.writeLog("message");

		verify(log).fatal("message");
	}

	@Test
	public void writeLogInfo() {
		commonsLogger.setLogLevel("INFO");

		commonsLogger.writeLog("message");

		verify(log).info("message");
	}
	
	@Test
	public void writeLogTrace() {
		commonsLogger.setLogLevel("TRAC");

		commonsLogger.writeLog("message");

		verify(log).trace("message");
	}
	
	@Test
	public void writeLogWarn() {
		commonsLogger.setLogLevel("WARN");

		commonsLogger.writeLog("message");

		verify(log).warn("message");
	}
}
