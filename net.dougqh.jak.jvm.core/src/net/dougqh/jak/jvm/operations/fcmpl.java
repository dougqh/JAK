package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fcmpl extends ComparisonOperation {
	public static final String ID = "fcmpl";
	public static final byte CODE = FCMPL;
	
	public static final fcmpl instance() {
		return new fcmpl();
	}
	
	private fcmpl() {}
	
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
		processor.fcmpl();
	}
}