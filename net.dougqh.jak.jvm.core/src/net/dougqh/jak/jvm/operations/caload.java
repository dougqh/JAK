package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class caload extends ArrayLoadOperation {
	public static final String ID = "caload";
	public static final byte CODE = CALOAD;
	
	public static final caload instance() {
		return new caload();
	}
	
	private caload() {}
	
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
		return char[].class;
	}
	
	@Override
	public final Type elementType() {
		return char.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.caload();
	}
}