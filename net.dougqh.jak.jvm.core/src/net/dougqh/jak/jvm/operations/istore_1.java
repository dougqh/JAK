package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class istore_1 extends FixedStoreOperation {
	public static final String ID = "istore_1";
	public static final byte CODE = ISTORE_1;
	
	public static final istore_1 instance() {
		return new istore_1();
	}
	
	private istore_1() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.istore_1();
	}
}