package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class laload extends ArrayLoadOperation {
	public static final String ID = "laload";
	public static final byte CODE = LALOAD;
	
	public static final laload instance() {
		return new laload();
	}
	
	private laload() {}
	
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
		return long[].class;
	}
	
	@Override
	public final Type elementType() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.laload();
	}
}