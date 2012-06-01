package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dconst_0 extends ConstantOperation {
	public static final String ID = "dconst_0";
	public static final byte CODE = DCONST_0;
	
	public static final dconst_0 instance() {
		return new dconst_0();
	}
	
	private dconst_0() {}
	
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
		return ConstantOperation.<T>as( 0D );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dconst_0();
	}
}