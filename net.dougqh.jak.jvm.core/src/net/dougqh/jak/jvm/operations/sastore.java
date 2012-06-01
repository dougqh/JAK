package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class sastore extends ArrayStoreOperation {
	public static final String ID = "sastore";
	public static final byte CODE = SASTORE;
	
	public static final sastore instance() {
		return new sastore();
	}
	
	private sastore() {}
	
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
		processor.sastore();
	}
}