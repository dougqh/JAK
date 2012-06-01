package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lconst_0 extends ConstantOperation {
	public static final String ID = "lconst_0";
	public static final byte CODE = LCONST_0;
	
	public static final lconst_0 instance() {
		return new lconst_0();
	}
	
	private lconst_0() {}
	
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
		return long.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( 0L );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lconst_0();
	}
}