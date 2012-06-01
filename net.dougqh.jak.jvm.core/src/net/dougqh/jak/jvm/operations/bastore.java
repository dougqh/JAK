package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class bastore extends ArrayStoreOperation {
	public static final String ID = "bastore";
	public static final byte CODE = BASTORE;
	
	public static final bastore instance() {
		return new bastore();
	}
	
	private bastore() {}
	
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
		processor.bastore();
	}
}