package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dstore_0 extends FixedStoreOperation {
	public static final String ID = "dstore_0";
	public static final byte CODE = DSTORE_0;
	
	public static final dstore_0 instance() {
		return new dstore_0();
	}
	
	private dstore_0() {}
	
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
	public final int slot() {
		return 0;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dstore_0();
	}
}