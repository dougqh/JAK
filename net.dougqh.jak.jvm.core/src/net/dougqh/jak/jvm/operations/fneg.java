package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fneg extends UnaryOperation {
	public static final String ID = "fneg";
	public static final byte CODE = FNEG;
	
	public static final fneg instance() {
		return new fneg();
	}
	
	private fneg() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final String getOperator() {
		return NEG;
	}
	
	@Override
	public final Type inputType() {
		return float.class;
	}
	
	@Override
	public final Type resultType() {
		return float.class;
	}
	
	@Override
	public final <T> T fold( final Object value ) {
		return UnaryOperation.<T>as( -(Float)value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fneg();
	}
}