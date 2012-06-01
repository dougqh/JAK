package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lastore extends ArrayStoreOperation {
	public static final String ID = "lastore";
	public static final byte CODE = LASTORE;
	
	public static final lastore instance() {
		return new lastore();
	}
	
	private lastore() {}
	
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
		processor.lastore();
	}
}