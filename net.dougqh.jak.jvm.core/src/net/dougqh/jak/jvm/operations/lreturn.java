package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lreturn extends ReturnOperation {
	public static final String ID = "lreturn";
	public static final byte CODE = LRETURN;
	
	public static final lreturn instance() {
		return new lreturn();
	}
	
	private lreturn() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type type() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lreturn();
	}
}