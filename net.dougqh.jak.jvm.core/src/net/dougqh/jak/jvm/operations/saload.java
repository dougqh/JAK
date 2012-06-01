package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class saload extends ArrayLoadOperation {
	public static final String ID = "saload";
	public static final byte CODE = SALOAD;
	
	public static final saload instance() {
		return new saload();
	}
	
	private saload() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type arrayType() {
		return short[].class;
	}
	
	@Override
	public final Type elementType() {
		return short.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.saload();
	}
}