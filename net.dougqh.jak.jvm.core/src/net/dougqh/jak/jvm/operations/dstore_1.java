package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dstore_1 extends FixedStoreOperation {
	public static final String ID = "dstore_1";
	public static final byte CODE = DSTORE_1;
	
	public static final dstore_1 instance() {
		return new dstore_1();
	}
	
	private dstore_1() {}
	
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
		return 1;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dstore_1();
	}
}