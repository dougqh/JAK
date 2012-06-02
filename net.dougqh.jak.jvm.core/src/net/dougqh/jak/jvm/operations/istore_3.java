package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class istore_3 extends FixedStoreOperation {
	public static final String ID = "istore_3";
	public static final byte CODE = ISTORE_3;
	
	public static final istore_3 instance() {
		return new istore_3();
	}
	
	private istore_3() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.istore_3();
	}
}