package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class i2b extends CastOperation {
	public static final String ID = "i2b";
	public static final byte CODE = I2B;
	
	public static final i2b instance() {
		return new i2b();
	}
	
	private i2b() {}
	
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
		return byte.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.i2b();
	}
}