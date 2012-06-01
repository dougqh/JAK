package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_m1 extends ConstantOperation {
	public static final String ID = "iconst_m1";
	public static final byte CODE = ICONST_M1;
	
	public static final iconst_m1 instance() {
		return new iconst_m1();
	}
	
	private iconst_m1() {}
	
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
		return int.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( -1 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_m1();
	}
}