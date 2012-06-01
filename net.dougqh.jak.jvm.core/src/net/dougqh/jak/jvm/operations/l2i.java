package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class l2i extends CastOperation {
	public static final String ID = "l2i";
	public static final byte CODE = L2I;
	
	public static final l2i instance() {
		return new l2i();
	}
	
	private l2i() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.l2i();
	}
}