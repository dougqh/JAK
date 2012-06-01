package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class i2f extends CastOperation {
	public static final String ID = "i2f";
	public static final byte CODE = I2F;
	
	public static final i2f instance() {
		return new i2f();
	}
	
	private i2f() {}
	
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
		return int.class;
	}
	
	@Override
	public final Type toType() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.i2f();
	}
}