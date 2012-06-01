package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lcmp extends ComparisonOperation {
	public static final String ID = "lcmp";
	public static final byte CODE = LCMP;
	
	public static final lcmp instance() {
		return new lcmp();
	}
	
	private lcmp() {}
	
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
		return long.class;
	}
	
	@Override
	public final Type rhsType() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lcmp();
	}
}