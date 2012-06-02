package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fload_1 extends FixedLoadOperation {
	public static final String ID = "fload_1";
	public static final byte CODE = FLOAD_1;
	
	public static final fload_1 instance() {
		return new fload_1();
	}
	
	private fload_1() {}
	
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
		processor.fload_1();
	}
}