package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class baload extends ArrayLoadOperation {
	public static final String ID = "baload";
	public static final byte CODE = BALOAD;
	
	public static final baload instance() {
		return new baload();
	}
	
	private baload() {}
	
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
		return byte[].class;
	}
	
	@Override
	public final Type elementType() {
		return byte.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.baload();
	}
}