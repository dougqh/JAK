package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dneg extends UnaryOperation {
	public static final String ID = "dneg";
	public static final byte CODE = DNEG;
	
	public static final dneg instance() {
		return new dneg();
	}
	
	private dneg() {}
	
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
		return double.class;
	}
	
	@Override
	public final Type resultType() {
		return double.class;
	}
	
	@Override
	public final <T> T fold( final Object value ) {
		return UnaryOperation.<T>as( -(Double)value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dneg();
	}
}