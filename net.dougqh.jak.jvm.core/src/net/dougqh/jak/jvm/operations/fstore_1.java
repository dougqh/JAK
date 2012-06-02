package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fstore_1 extends FixedStoreOperation {
	public static final String ID = "fstore_1";
	public static final byte CODE = FSTORE_1;
	
	public static final fstore_1 instance() {
		return new fstore_1();
	}
	
	private fstore_1() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final int slot() {
		return 1;
	}
	
	@Override
	public final Type type() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fstore_1();
	}
}