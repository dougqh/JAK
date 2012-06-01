package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class d2i extends CastOperation {
	public static final String ID = "d2i";
	public static final byte CODE = D2I;
	
	public static final d2i instance() {
		return new d2i();
	}
	
	private d2i() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.d2i();
	}
}