package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class d2f extends CastOperation {
	public static final String ID = "d2f";
	public static final byte CODE = D2F;
	
	public static final d2f instance() {
		return new d2f();
	}
	
	private d2f() {}
	
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
		return double.class;
	}
	
	@Override
	public final Type toType() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.d2f();
	}
}