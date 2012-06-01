package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dcmpg extends ComparisonOperation {
	public static final String ID = "dcmpg";
	public static final byte CODE = DCMPG;
	
	public static final dcmpg instance() {
		return new dcmpg();
	}
	
	private dcmpg() {}
	
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
		return double.class;
	}
	
	@Override
	public final Type rhsType() {
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dcmpg();
	}
}