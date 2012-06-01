package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class i2s extends CastOperation {
	public static final String ID = "i2s";
	public static final byte CODE = I2S;
	
	public static final i2s instance() {
		return new i2s();
	}
	
	private i2s() {}
	
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
		return short.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.i2s();
	}
}