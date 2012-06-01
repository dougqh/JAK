package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_2 extends ConstantOperation {
	public static final String ID = "iconst_2";
	public static final byte CODE = ICONST_2;
	
	public static final iconst_2 instance() {
		return new iconst_2();
	}
	
	private iconst_2() {}
	
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
		return ConstantOperation.<T>as( 2 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_2();
	}
}