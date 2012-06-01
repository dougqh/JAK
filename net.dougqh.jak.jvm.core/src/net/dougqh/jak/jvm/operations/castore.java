package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class castore extends ArrayStoreOperation {
	public static final String ID = "castore";
	public static final byte CODE = CASTORE;
	
	public static final castore instance() {
		return new castore();
	}
	
	private castore() {}
	
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
		processor.castore();
	}
}