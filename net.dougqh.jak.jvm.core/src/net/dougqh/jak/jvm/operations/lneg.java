package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lneg extends UnaryOperation {
	public static final String ID = "lneg";
	public static final byte CODE = LNEG;
	
	public static final lneg instance() {
		return new lneg();
	}
	
	private lneg() {}
	
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
		return long.class;
	}
	
	@Override
	public final Type resultType() {
		return long.class;
	}
	
	@Override
	public final <T> T fold( final Object value ) {
		return UnaryOperation.<T>as( -(Long)value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lneg();
	}
}