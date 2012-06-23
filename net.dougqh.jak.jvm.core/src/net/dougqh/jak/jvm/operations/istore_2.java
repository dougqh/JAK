package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class istore_2 extends FixedStoreOperation {
	public static final String ID = "istore_2";
	public static final byte CODE = ISTORE_2;
	
	public static final istore_2 instance() {
		return new istore_2();
	}
	
	private istore_2() {}
	
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
		return 2;
	}
	
	@Override
	public final Type type() {
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.istore_2();
	}
}