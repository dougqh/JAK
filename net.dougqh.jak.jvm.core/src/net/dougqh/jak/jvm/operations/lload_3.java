package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lload_3 extends LoadOperation {
	public static final String ID = "lload_3";
	public static final byte CODE = LLOAD_3;
	
	public static final lload_3 instance() {
		return new lload_3();
	}
	
	private lload_3() {}
	
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
		return 3;
	}
	
	@Override
	public final Type type() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lload_3();
	}
}