package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class ineg extends UnaryOperation {
	public static final String ID = "ineg";
	public static final byte CODE = INEG;
	
	public static final ineg instance() {
		return new ineg();
	}
	
	private ineg() {}
	
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
		return int.class;
	}
	
	@Override
	public final Type resultType() {
		return int.class;
	}
	
	@Override
	public final <T> T fold( final Object value ) {
		return UnaryOperation.<T>as( -(Integer)value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ineg();
	}
}