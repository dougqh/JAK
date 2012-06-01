package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lstore_2 extends StoreOperation {
	public static final String ID = "lstore_2";
	public static final byte CODE = LSTORE_2;
	
	public static final lstore_2 instance() {
		return new lstore_2();
	}
	
	private lstore_2() {}
	
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
		return 2;
	}
	
	@Override
	public final Type type() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lstore_2();
	}
}