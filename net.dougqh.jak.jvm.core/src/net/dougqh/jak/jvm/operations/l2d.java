package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class l2d extends CastOperation {
	public static final String ID = "l2d";
	public static final byte CODE = L2D;
	
	public static final l2d instance() {
		return new l2d();
	}
	
	private l2d() {}
	
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
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.l2d();
	}
}