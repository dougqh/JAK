package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lstore_3 extends FixedStoreOperation {
	public static final String ID = "lstore_3";
	public static final byte CODE = LSTORE_3;
	
	public static final lstore_3 instance() {
		return new lstore_3();
	}
	
	private lstore_3() {}
	
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
		return 3;
	}
	
	@Override
	public final Type type() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lstore_3();
	}
}