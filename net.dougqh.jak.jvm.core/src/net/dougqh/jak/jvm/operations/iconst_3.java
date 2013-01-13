package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_3 extends FixedConstantOperation {
	public static final String ID = "iconst_3";
	public static final byte CODE = ICONST_3;
	
	public static final iconst_3 instance() {
		return new iconst_3();
	}
	
	private iconst_3() {}
	
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
		return ConstantOperation.<T>as( 3 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_3();
	}
}