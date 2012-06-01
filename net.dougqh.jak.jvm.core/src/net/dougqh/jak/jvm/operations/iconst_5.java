package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_5 extends ConstantOperation {
	public static final String ID = "iconst_5";
	public static final byte CODE = ICONST_5;
	
	public static final iconst_5 instance() {
		return new iconst_5();
	}
	
	private iconst_5() {}
	
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
		return ConstantOperation.<T>as( 5 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_5();
	}
}