package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class freturn extends ReturnOperation {
	public static final String ID = "freturn";
	public static final byte CODE = FRETURN;
	
	public static final freturn instance() {
		return new freturn();
	}
	
	private freturn() {}
	
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
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.freturn();
	}
}