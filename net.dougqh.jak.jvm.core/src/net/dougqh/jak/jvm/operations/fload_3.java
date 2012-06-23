package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fload_3 extends FixedLoadOperation {
	public static final String ID = "fload_3";
	public static final byte CODE = FLOAD_3;
	
	public static final fload_3 instance() {
		return new fload_3();
	}
	
	private fload_3() {}
	
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
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fload_3();
	}
}