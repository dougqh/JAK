package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dstore_2 extends StoreOperation {
	public static final String ID = "dstore_2";
	public static final byte CODE = DSTORE_2;
	
	public static final dstore_2 instance() {
		return new dstore_2();
	}
	
	private dstore_2() {}
	
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
	public final boolean isFixed() {
		return true;
	}
	
	@Override
	public final int slot() {
		return 2;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dstore_1();
	}
}