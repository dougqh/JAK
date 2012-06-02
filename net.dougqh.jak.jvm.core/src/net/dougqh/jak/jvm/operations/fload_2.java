package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fload_2 extends FixedLoadOperation {
	public static final String ID = "fload_2";
	public static final byte CODE = FLOAD_2;
	
	public static final fload_2 instance() {
		return new fload_2();
	}
	
	private fload_2() {}
	
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
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fload_2();
	}
}