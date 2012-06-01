package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class f2d extends CastOperation {
	public static final String ID = "f2d";
	public static final byte CODE = F2D;
	
	public static final f2d instance() {
		return new f2d();
	}
	
	private f2d() {}
	
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
		return float.class;
	}
	
	@Override
	public Type toType() {
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.f2d();
	}
}