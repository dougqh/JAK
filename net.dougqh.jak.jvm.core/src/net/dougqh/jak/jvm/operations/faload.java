package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class faload extends ArrayLoadOperation {
	public static final String ID = "faload";
	public static final byte CODE = FALOAD;
	
	public static final faload instance() {
		return new faload();
	}
	
	private faload() {}
	
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
		return float[].class;
	}
	
	@Override
	public final Type elementType() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.faload();
	}
}