package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fload_0 extends LoadOperation {
	public static final String ID = "fload_0";
	public static final byte CODE = FLOAD_0;
	
	public static final fload_0 instance() {
		return new fload_0();
	}
	
	private fload_0() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final boolean isFixed() {
		return true;
	}
	
	@Override
	public final int slot() {
		return 0;
	}
	
	@Override
	public final Type type() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fload_0();
	}
}