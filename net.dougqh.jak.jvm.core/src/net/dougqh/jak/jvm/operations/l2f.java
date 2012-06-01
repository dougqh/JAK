package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class l2f extends CastOperation {
	public static final String ID = "l2f";
	public static final byte CODE = L2F;
	
	public static final l2f instance() {
		return new l2f();
	}
	
	private l2f() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type fromType() {
		return long.class;
	}
	
	@Override
	public final Type toType() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.l2f();
	}
}