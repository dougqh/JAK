package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fcmpg extends ComparisonOperation {
	public static final String ID = "fcmpg";
	public static final byte CODE = FCMPG;
	
	public static final fcmpg instance() {
		return new fcmpg();
	}
	
	private fcmpg() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type lhsType() {
		return float.class;
	}

	@Override
	public final Type rhsType() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fcmpg();
	}
}