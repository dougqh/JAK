package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dastore extends ArrayStoreOperation {
	public static final String ID = "dastore";
	public static final byte CODE = DASTORE;
	
	public static final dastore instance() {
		return new dastore();
	}
	
	private dastore() {}
	
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
		return double[].class;
	}
	
	@Override
	public final Type elementType() {
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dastore();
	}
}