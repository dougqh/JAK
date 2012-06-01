package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dstore_3 extends StoreOperation {
	public static final String ID = "dstore_3";
	public static final byte CODE = DSTORE_3;
	
	public static final dstore_3 instance() {
		return new dstore_3();
	}
	
	private dstore_3() {}
	
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
		return 3;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dstore_3();
	}
}