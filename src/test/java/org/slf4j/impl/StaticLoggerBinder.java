package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/** 
 * Binder for testing SLF4J code
 * 
 * @author Niall Thomson
 */
public class StaticLoggerBinder {
	private static StaticLoggerBinder instance;
	
	private Logger logger;
	
	public static StaticLoggerBinder getSingleton() {
		if(instance == null) {
			instance = new StaticLoggerBinder();
		}
		
		return instance;
	}
	
	public ILoggerFactory getLoggerFactory() {
		ILoggerFactory factory = mock(ILoggerFactory.class);
		
		when(factory.getLogger(anyString())).thenReturn(logger);
		
		return factory;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
