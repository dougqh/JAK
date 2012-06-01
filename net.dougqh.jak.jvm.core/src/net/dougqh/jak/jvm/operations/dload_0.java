package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dload_0 extends LoadOperation {
	public static final String ID = "dload_0";
	public static final byte CODE = DLOAD_0;
	
	public static final dload_0 instance() {
		return new dload_0();
	}
	
	private dload_0() {}
	
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
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dload_0();
	}
}