package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dconst_1 extends ConstantOperation {
	public static final String ID = "dconst_1";
	public static final byte CODE = DCONST_1;
	
	public static final dconst_1 instance() {
		return new dconst_1();
	}
	
	private dconst_1() {}
	
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
		return double.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( 1D );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dconst_1();
	}
}