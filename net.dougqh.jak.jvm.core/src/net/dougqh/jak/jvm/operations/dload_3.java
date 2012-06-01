package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dload_3 extends LoadOperation {
	public static final String ID = "dload_3";
	public static final byte CODE = DLOAD_3;
	
	public static final dload_3 instance() {
		return new dload_3();
	}
	
	private dload_3() {}
	
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
		processor.dload_3();
	}
}