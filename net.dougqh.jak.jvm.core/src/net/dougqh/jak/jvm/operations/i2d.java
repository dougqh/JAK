package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class i2d extends CastOperation {
	public static final String ID = "i2d";
	public static final byte CODE = I2D;
	
	public static final i2d instance() {
		return new i2d();
	}
	
	private i2d() {}
	
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
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.i2d();
	}
}