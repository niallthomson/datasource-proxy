package net.ttddyy.dsproxy.proxy.delegating;

import java.sql.Statement;

public class DelegatingStatement extends AbstractDelegatingStatement<Statement> {
	public DelegatingStatement(Statement delegate) {
		super(delegate);
	}
}
