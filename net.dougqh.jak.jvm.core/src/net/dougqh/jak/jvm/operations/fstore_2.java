package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fstore_2 extends StoreOperation {
	public static final String ID = "fstore_2";
	public static final byte CODE = FSTORE_2;
	
	public static final fstore_2 instance() {
		return new fstore_2();
	}
	
	private fstore_2() {}
	
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
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fstore_2();
	}
}