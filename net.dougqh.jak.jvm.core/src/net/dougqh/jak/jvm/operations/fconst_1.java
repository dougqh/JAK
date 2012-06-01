package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fconst_1 extends ConstantOperation {
	public static final String ID = "fconst_1";
	public static final byte CODE = FCONST_1;
	
	public static final fconst_1 instance() {
		return new fconst_1();
	}
	
	private fconst_1() {}
	
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
		return float.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( 1F );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fconst_1();
	}
}