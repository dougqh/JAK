package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fconst_0 extends FixedConstantOperation {
	public static final String ID = "fconst_0";
	public static final byte CODE = FCONST_0;
	
	public static final fconst_0 instance() {
		return new fconst_0();
	}
	
	private fconst_0() {}
	
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
		return ConstantOperation.<T>as( 0F );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fconst_0();
	}
}