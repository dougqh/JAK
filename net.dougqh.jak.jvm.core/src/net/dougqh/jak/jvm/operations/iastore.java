package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iastore extends ArrayStoreOperation {
	public static final String ID = "iastore";
	public static final byte CODE = IASTORE;
	
	public static final iastore instance() {
		return new iastore();
	}
	
	private iastore() {}
	
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
		processor.iastore();
	}
}