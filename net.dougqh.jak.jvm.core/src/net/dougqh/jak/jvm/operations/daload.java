package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class daload extends ArrayLoadOperation {
	public static final String ID = "daload";
	public static final byte CODE = DALOAD;
	
	public static final daload instance() {
		return new daload();
	}
	
	private daload() {}
	
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
	public final void process(final JvmOperationProcessor processor) {
		processor.daload();
	}
}