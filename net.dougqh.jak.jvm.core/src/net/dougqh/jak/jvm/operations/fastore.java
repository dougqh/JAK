package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fastore extends ArrayStoreOperation {
	public static final String ID = "fastore";
	public static final byte CODE = FASTORE;
	
	public static final fastore instance() {
		return new fastore();
	}
	
	private fastore() {}
	
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
		processor.fastore();
	}
}