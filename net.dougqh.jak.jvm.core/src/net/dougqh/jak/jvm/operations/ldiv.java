package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class ldiv extends BinaryOperation {
	public static final String ID = "ldiv";
	public static final byte CODE = LDIV;
	
	public static final ldiv instance() {
		return new ldiv();
	}
	
	private ldiv() {}
	
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
		return DIV;
	}
	
	@Override
	public final Type lhsType() {
		return long.class;
	}
	
	@Override
	public final Type rhsType() {
		return long.class;
	}
	
	@Override
	public final Type resultType() {
		return long.class;
	}
	
	@Override
	public final <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Long)lhs / (Long)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ldiv();
	}
}