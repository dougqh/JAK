package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dreturn extends ReturnOperation {
	public static final String ID = "dreturn";
	public static final byte CODE = DRETURN;
	
	public static final dreturn instance() {
		return new dreturn();
	}
	
	private dreturn() {}
	
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
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dreturn();
	}
}