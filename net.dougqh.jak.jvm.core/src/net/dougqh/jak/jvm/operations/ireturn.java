package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class ireturn extends ReturnOperation {
	public static final String ID = "ireturn";
	public static final byte CODE = IRETURN;
	
	public static final ireturn instance() {
		return new ireturn();
	}
	
	private ireturn() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ireturn();
	}
}