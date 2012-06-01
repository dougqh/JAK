package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aaload extends ArrayLoadOperation {
	public static final String ID = "aaload";
	public static final byte CODE = AALOAD;
	
	public static final aaload instance() {
		return new aaload();
	}
	
	private aaload() {}
	
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
		processor.aaload();
	}
}