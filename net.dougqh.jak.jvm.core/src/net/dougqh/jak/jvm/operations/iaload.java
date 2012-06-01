package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iaload extends ArrayLoadOperation {
	public static final String ID = "iaload";
	public static final byte CODE = IALOAD;
	
	public static final iaload instance() {
		return new iaload();
	}
	
	private iaload() {}
	
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
		return int[].class;
	}
	
	@Override
	public final Type elementType() {
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iaload();
	}
}