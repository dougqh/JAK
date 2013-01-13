package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_4 extends FixedConstantOperation {
	public static final String ID = "iconst_4";
	public static final byte CODE = ICONST_4;
	
	public static final iconst_4 instance() {
		return new iconst_4();
	}
	
	private iconst_4() {}
	
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
		return ConstantOperation.<T>as( 4 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_4();
	}
}