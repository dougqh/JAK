package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fconst_2 extends FixedConstantOperation {
	public static final String ID = "fconst_2";
	public static final byte CODE = FCONST_2;
	
	public static final fconst_2 instance() {
		return new fconst_2();
	}
	
	private fconst_2() {}
	
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
		return ConstantOperation.<T>as( 2F );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fconst_2();
	}
}