package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class d2l extends CastOperation {
	public static final String ID = "d2l";
	public static final byte CODE = D2L;
	
	public static final d2l instance() {
		return new d2l();
	}
	
	private d2l() {}
	
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
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.d2l();
	}
}