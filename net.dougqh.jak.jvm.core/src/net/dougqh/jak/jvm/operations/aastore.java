package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aastore extends ArrayStoreOperation {
	public static final String ID = "aastore";
	public static final byte CODE = AASTORE;
	
	public static final aastore instance() {
		return new aastore();
	}
	
	private aastore() {}
	
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
		return Reference[].class;
	}
	
	@Override
	public final Type elementType() {
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.aastore();
	}
}